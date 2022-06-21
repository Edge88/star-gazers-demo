package com.ldm.stargazer.ui.search

import androidx.lifecycle.*
import com.ldm.stargazer.data.repositories.parceable.SearchData
import com.ldm.stargazer.util.SingleLiveEvent
import com.ldm.stargazer.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    //databinding params

    val owner = MutableLiveData<String>()
    val repo = MutableLiveData<String>()

    val repoError = MutableLiveData<ValidationUtils.ValidationResultEnum>()
    val ownerError = MutableLiveData<ValidationUtils.ValidationResultEnum>()

    var repoValid = ValidationUtils.ValidationResponse(ValidationUtils.ValidationResultEnum.UNDEFINED)
    var userValid = ValidationUtils.ValidationResponse(ValidationUtils.ValidationResultEnum.UNDEFINED)

    val buttonEnabled = MutableLiveData(false)
    val startSearch = SingleLiveEvent<SearchData>()


     // validate repository name and update UI
    fun validateRepoField(){
         repoValid = ValidationUtils.validateRepository(repo.value)
         repoError.value = repoValid.result
         Timber.tag(SEARCH_LOG).d("Field repository validation result: $repoValid")
         buttonEnabled.value = repoValid.result == ValidationUtils.ValidationResultEnum.OK && userValid.result == ValidationUtils.ValidationResultEnum.OK

    }

    // validate owner name and update UI
    fun validateOwnerField(){
        userValid = ValidationUtils.validateOwner(owner.value)
        ownerError.value = userValid.result
        Timber.tag(SEARCH_LOG).d("Field owner validation result: $userValid")
        buttonEnabled.value = repoValid.result == ValidationUtils.ValidationResultEnum.OK && userValid.result == ValidationUtils.ValidationResultEnum.OK
    }


    fun search(){
        startSearch.value =SearchData(owner.value!!.trim(),repo.value!!.trim())
    }



}







