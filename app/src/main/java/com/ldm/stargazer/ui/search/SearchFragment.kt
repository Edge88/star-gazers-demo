package com.ldm.stargazer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ldm.stargazer.R
import com.ldm.stargazer.databinding.SearchBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * [Fragment] to sets search params and call star gazers API
 */
const val SEARCH_LOG ="SEARCH_LOG"
@AndroidEntryPoint
class SearchFragment : Fragment(){


    private lateinit var binding: SearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.search,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel=searchViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.repo.observe(viewLifecycleOwner) {
            searchViewModel.validateRepoField()
        }

        searchViewModel.owner.observe(viewLifecycleOwner) {
            searchViewModel.validateOwnerField()
        }

        searchViewModel.startSearch.observe(viewLifecycleOwner){
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToStarGazersFragment(it))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }



}
