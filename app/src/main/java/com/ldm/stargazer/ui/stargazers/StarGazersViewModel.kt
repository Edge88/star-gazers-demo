package com.ldm.stargazer.ui.stargazers

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ldm.stargazer.api.response.StarGazersUser
import com.ldm.stargazer.data.repositories.StarGazersRepository
import com.ldm.stargazer.data.repositories.parceable.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StarGazersViewModel @Inject constructor(
    private val starGazersRepository: StarGazersRepository,
    private val state: SavedStateHandle)
    : ViewModel() {

    // MediatorLiveData object to handle API calls and notify to observer source value modifications
    val retrievedUsers = MediatorLiveData<PagingData<StarGazersUser>>()

    init {
        launch()
    }


    // Update retrievedUsers with star gazers API call responses
    private fun launch() {
        //retrieved from SavedStateHandle with DI
        val data = state.get<SearchData>("QueryData")!!
        Timber.tag(STAR_GAZERS_LOG).d("Lauching star glazers call with params: $data")
        retrievedUsers.addSource(
        starGazersRepository.getStarGazersFromUserAndRepoName(data.owner, data.repoName).cachedIn(viewModelScope)) {
        retrievedUsers.value = it
        }

    }

}

