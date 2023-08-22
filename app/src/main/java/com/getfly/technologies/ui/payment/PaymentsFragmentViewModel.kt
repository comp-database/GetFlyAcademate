package com.getfly.technologies.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.response.FeeDetailsResponse
import com.getfly.technologies.model.response.FeeStructureResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class PaymentsFragmentViewModel(private val repository: AcademateRepository, private val repositoryTwo: EaseBuzzRepository) : ViewModel() {
    var FeeStructureResponse: MutableLiveData<Response<FeeStructureResponse>> = MutableLiveData()
    var FeeDetailsResponse: MutableLiveData<Response<FeeDetailsResponse>> = MutableLiveData()

    fun getFeeStructure(uid: String) {
        viewModelScope.launch {
            val response = repository.getFeeStructure(uid)
            FeeStructureResponse.value = response
        }
    }
}