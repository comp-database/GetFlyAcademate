package com.getfly.technologies.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.response.CurrentCourseDetailsResponse
import com.getfly.technologies.model.response.DocResponse
import com.getfly.technologies.model.response.PersonalDetailsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileFragmentViewModel(private val repository: AcademateRepository) : ViewModel()  {

    var PersonalDetailsResponse: MutableLiveData<Response<PersonalDetailsResponse>> =
        MutableLiveData()
    var CurrentCourseDetailsResponse: MutableLiveData<Response<CurrentCourseDetailsResponse>> =
        MutableLiveData()
    var DocDeatailResponse: MutableLiveData<Response<DocResponse>> = MutableLiveData()


    fun getPersonalDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getPersonalDetails(uid)
            PersonalDetailsResponse.value = response
        }
    }

    fun getCurrentCourseDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getCurrentCourseDetails(uid)
            CurrentCourseDetailsResponse.value = response
        }
    }

    fun getDocDetails(uid: String?) {
        viewModelScope.launch {
            val response = repository.getDocDetails(uid)
            DocDeatailResponse.value = response
        }
    }

}