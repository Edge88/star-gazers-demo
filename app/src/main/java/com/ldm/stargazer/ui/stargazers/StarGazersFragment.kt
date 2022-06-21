package com.ldm.stargazer.ui.stargazers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ldm.stargazer.R
import com.ldm.stargazer.databinding.StarGazersBinding
import com.ldm.stargazer.ui.adapter.StartGazersAdapter
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import timber.log.Timber

/**
 * [Fragment] to show retrieved users from star gazers API calls and handle errors
 */

const val STAR_GAZERS_LOG = "STAR_GAZERS_LOG"

@AndroidEntryPoint
class StarGazersFragment : Fragment(){


    private lateinit var binding: StarGazersBinding
    private val starGazersViewModel: StarGazersViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = StarGazersBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StartGazersAdapter()

        //for databinding
        binding.viewmodel = starGazersViewModel


        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            buttonHome.setOnClickListener{findNavController().popBackStack()}
            buttonRetry.setOnClickListener { adapter.retry() }
        }

        //handles Paging adapter load state to manage errors
        adapter.addLoadStateListener { loadState->
            val ref = loadState.source.refresh
            if(ref is LoadState.Error && ref.error is HttpException){
                handleHttpErrors(ref.error as HttpException)
            }
            else{
                if (ref is LoadState.Error){
                    binding.textErr.text = resources.getString(R.string.no_connection)
                }
                handleVisibilityComponentErr(ref is LoadState.Error,false)

            }
        }

        //observe page loading
        starGazersViewModel.retrievedUsers.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

    }


     // Update fields in view to show https errors from API calls
    private fun handleHttpErrors(err: HttpException){
         Timber.tag(STAR_GAZERS_LOG).d("handleHttpErrors code: $err")
         when(err.code()){
            403 -> binding.textErr.text = resources.getString(R.string.limit_exceeded)
            404 -> binding.textErr.text = resources.getString(R.string.not_found)
        }
         handleVisibilityComponentErr(isError= true, asHttpCode = true)


    }
    // Update fields in view to show errors from API calls
    private fun handleVisibilityComponentErr(isError: Boolean,asHttpCode: Boolean){
        binding.textErr.visibility = if(isError) View.VISIBLE else View.GONE
        binding.buttonHome.visibility = if(isError && asHttpCode) View.VISIBLE else View.GONE
        binding.buttonRetry.visibility = if(isError && !asHttpCode) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if(isError) View.GONE else View.VISIBLE

    }

    //to avoid mem leaks
    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

}
