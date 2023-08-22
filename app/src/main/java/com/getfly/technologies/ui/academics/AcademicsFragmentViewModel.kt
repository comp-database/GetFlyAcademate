package com.getfly.technologies.ui.academics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.response.EducationDetailsResponse
import com.getfly.technologies.model.response.SemDetailsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class AcademicsFragmentViewModel(private val repository: AcademateRepository) : ViewModel() {
    var EducationDetailsResponse: MutableLiveData<Response<EducationDetailsResponse>> =
        MutableLiveData()
    var SemDetailsResponse: MutableLiveData<Response<SemDetailsResponse>> = MutableLiveData()

    fun getEducationDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getEducationDetails(uid)
            EducationDetailsResponse.value = response
        }
    }

    fun getSemDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getSemDetails(uid)
            SemDetailsResponse.value = response
        }
    }
}