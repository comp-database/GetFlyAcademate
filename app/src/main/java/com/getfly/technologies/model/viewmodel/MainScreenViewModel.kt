package com.getfly.technologies.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.response.CurrentCourseDetailsResponse
import com.getfly.technologies.model.response.DocResponse
import com.getfly.technologies.model.response.EducationDetailsResponse
import com.getfly.technologies.model.response.FacultyDashboardResponse
import com.getfly.technologies.model.response.FeeDetailsResponse
import com.getfly.technologies.model.response.FeeStructureResponse
import com.getfly.technologies.model.response.InitiatePaymentBodyResponse
import com.getfly.technologies.model.response.InitiatePaymentResponse
import com.getfly.technologies.model.response.LoginResponse
import com.getfly.technologies.model.response.PendingApplicationResponse
import com.getfly.technologies.model.response.PersonalDetailsResponse
import com.getfly.technologies.model.response.SemDetailsResponse
import com.getfly.technologies.model.response.TransactionAPIResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainScreenViewModel(
    private val repository: AcademateRepository,
    private val repositoryTwo: EaseBuzzRepository
) : ViewModel() {
    var LoginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    var PersonalDetailsResponse: MutableLiveData<Response<PersonalDetailsResponse>> =
        MutableLiveData()
    var FeeDetailsResponse: MutableLiveData<Response<FeeDetailsResponse>> = MutableLiveData()
    var CurrentCourseDetailsResponse: MutableLiveData<Response<CurrentCourseDetailsResponse>> =
        MutableLiveData()
    var DocDeatailResponse: MutableLiveData<Response<DocResponse>> = MutableLiveData()
    var FacultyDashboardResponse: MutableLiveData<Response<FacultyDashboardResponse>> =
        MutableLiveData()
    var EducationDetailsResponse: MutableLiveData<Response<EducationDetailsResponse>> =
        MutableLiveData()
    var SemDetailsResponse: MutableLiveData<Response<SemDetailsResponse>> = MutableLiveData()
    var FeeStructureResponse: MutableLiveData<Response<FeeStructureResponse>> = MutableLiveData()
    var TransactionAPIResponse: MutableLiveData<Response<TransactionAPIResponse>> = MutableLiveData()
//    var PendingApplicationResponse: MutableLiveData<Response<PendingApplicationResponse>> = MutableLiveData()

    var InitiatePaymentBodyResponse: MutableLiveData<Response<InitiatePaymentBodyResponse>> =
        MutableLiveData()
    var InitiatePaymentResponse: MutableLiveData<Response<InitiatePaymentResponse.InitiatePaymentResponseSuccess>> =
        MutableLiveData()

    private val _pendingApplicationsLiveData =
        MutableLiveData<List<PendingApplicationResponse.PendingApplicationResponseItem>>()
    val pendingApplicationsLiveData: LiveData<List<PendingApplicationResponse.PendingApplicationResponseItem>>
        get() = _pendingApplicationsLiveData

    //payment response
    var PaymentResponse: MutableLiveData<Response<InitiatePaymentResponse>> = MutableLiveData()

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean>
        get() = _isLogin


//    fun postInitiatePayment(status: Int, data: String) {
//        viewModelScope.launch {
//            val response = repository.postInitiatePayment(status,data)
//            PaymentResponse.value = response
//        }
//    }

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.postLogin(email, password)
            LoginResponse.value = response
        }
    }

    fun getPersonalDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getPersonalDetails(uid)
            PersonalDetailsResponse.value = response
        }
    }

    fun getFeeDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getFeeDetails(uid)
            FeeDetailsResponse.value = response
        }
    }

    fun getCurrentCourseDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getCurrentCourseDetails(uid)
            CurrentCourseDetailsResponse.value = response
        }
    }

//    fun PermanentLogin(email: String, password: String) {
//        viewModelScope.launch {
//            val response = repository.postLogin(email,password)
//            _isLogin.value = response.body()!!.isLogin
//        }
//    }

    fun getDocDetails(uid: String?) {
        viewModelScope.launch {
            val response = repository.getDocDetails(uid)
            DocDeatailResponse.value = response
        }
    }

    fun getfacultyDashboard(uid: String?) {
        viewModelScope.launch {
            val response = repository.getfacultyDashboard(uid)
            FacultyDashboardResponse.value = response
        }
    }

    //    suspend fun getPendingList(uid : String?):List<PendingApplicationResponse.PendingApplicationResponseItem>{
//        return repository.getPendingApplication(uid)
//    }
    fun getPendingApplication(uid: String?) {
        viewModelScope.launch {
            val response = repository.getPendingApplication(uid)
            _pendingApplicationsLiveData.value = response
        }
    }

    fun getEducationDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getEducationDetails(uid)
            EducationDetailsResponse.value = response
        }
    }

//    fun getSemDetails(uid : String) {
//        viewModelScope.launch {
//            val response = repository.getSemDetails(uid)
//            SemDetailsResponse.value = response
//        }
//    }

    fun getSemDetails(uid: String) {
        viewModelScope.launch {
            val response = repository.getSemDetails(uid)
            SemDetailsResponse.value = response
        }
    }

    fun getFeeStructure(uid: String) {
        viewModelScope.launch {
            val response = repository.getFeeStructure(uid)
            FeeStructureResponse.value = response
        }
    }

    fun postDataToInitiatePayment(
        amount: Float,
        txnid: String,
        email: String,
        name: String,
        phone: String,
        productinfo: String,
        hash: String
    ) {
        viewModelScope.launch {
            val response = repositoryTwo.postDataToInitiatePayment(
                amount,
                txnid,
                email,
                name,
                phone,
                productinfo,
                hash
            )
            InitiatePaymentResponse.value = response
        }
    }

    fun getInitiatePaymentBodyDetails(uid: String?) {
        viewModelScope.launch {
            val response = repository.getInitiatePaymentBodyDetails(uid)
            InitiatePaymentBodyResponse.value = response
        }
    }


    fun postTransactionPaymentData(
        txnid: String,
        email: String,
        phone: String,
        amount: Float,
        hash: String,
    ) {
        viewModelScope.launch {
            val response = repositoryTwo.postTransactionPaymentData(
                txnid,
                email,
                phone,
                amount,
                hash
            )
            TransactionAPIResponse.value = response
        }
    }


}