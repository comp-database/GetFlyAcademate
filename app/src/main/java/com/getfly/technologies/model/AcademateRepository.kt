package com.getfly.technologies.model

import com.getfly.technologies.model.api.AcademateWebService
import com.getfly.technologies.model.api.EaseBuzzWebService
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
import retrofit2.Response

class AcademateRepository(private val webService : AcademateWebService = AcademateWebService(),
                          private val paymentWebService: EaseBuzzWebService = EaseBuzzWebService()
) {
    suspend fun postLogin( email: String, password: String): Response<LoginResponse> {
        var response = webService.api.postLogin(email,password)
        return response
    }

    suspend fun getPersonalDetails(uid: String):Response<PersonalDetailsResponse> {
        return webService.api.getPersonalDetails(uid)
    }

    suspend fun getFeeDetails(uid: String):Response<FeeDetailsResponse> {
        return webService.api.getFeeDetails(uid)
    }

    suspend fun getCurrentCourseDetails(uid: String):Response<CurrentCourseDetailsResponse> {
        return webService.api.getCurrentCourseDetails(uid)
    }

    suspend fun getDocDetails(uid : String?) : Response<DocResponse>{
        return webService.api.getDocDetails(uid)
    }

    suspend fun getfacultyDashboard(uid : String?) : Response<FacultyDashboardResponse>{
        return webService.api.getfacultyDashboard(uid)
    }

    suspend fun getPendingApplication(uid : String?) : List<PendingApplicationResponse.PendingApplicationResponseItem>{
//        val response = webService.api.getPendingApplication(uid)
        return webService.api.getPendingApplication(uid)
    }

    //Payment post request
//    suspend fun postInitiatePayment(status: Int, data: String): Response<InitiatePaymentResponse> {
//        var response = paymentWebService.easeBuzzApi.postInitiatePayment(status, data)
//        return response
//    }

    suspend fun getInitiatePaymentBodyDetails(uid : String?) : Response<InitiatePaymentBodyResponse>{
        return webService.api.getInitiatePaymentBodyDetails(uid)
    }

    suspend fun getEducationDetails(uid: String):Response<EducationDetailsResponse> {
        return webService.api.getEducationDetails(uid)
    }

    suspend fun getSemDetails(uid: String):Response<SemDetailsResponse> {
        return webService.api.getSemDetails(uid)
    }

    suspend fun getFeeStructure(uid: String):Response<FeeStructureResponse> {
        return webService.api.getFeeStructure(uid)
    }

}