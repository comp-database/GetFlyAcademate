package com.getfly.technologies.model.api

import com.getfly.technologies.model.response.CurrentCourseDetailsResponse
import com.getfly.technologies.model.response.DocResponse
import com.getfly.technologies.model.response.EducationDetailsResponse
import com.getfly.technologies.model.response.FacultyDashboardResponse
import com.getfly.technologies.model.response.FeeDetailsResponse
import com.getfly.technologies.model.response.FeeStructureResponse
import com.getfly.technologies.model.response.InitiatePaymentBodyResponse
import com.getfly.technologies.model.response.LoginResponse
import com.getfly.technologies.model.response.PendingApplicationResponse
import com.getfly.technologies.model.response.PersonalDetailsResponse
import com.getfly.technologies.model.response.SemDetailsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class AcademateWebService {
    var api: AcademateApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://vppcoe-va.getflytechnologies.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AcademateApi::class.java)
    }

    interface AcademateApi {

        @FormUrlEncoded
        @POST("api/login")
        suspend fun postLogin(
            @Field("email") email: String,
            @Field("password") password: String
        ): Response<LoginResponse>

        @GET("api/admission/personalDetails")
        suspend fun getPersonalDetails(
            @Query("uid")
            uid : String?
        ) : Response<PersonalDetailsResponse>

        @GET("/api/admission/feeBalanceStud")
        suspend fun getFeeDetails(
            @Query("uid")
            uid : String?
        ) : Response<FeeDetailsResponse>

        @GET("/api/admission/currentEducation_per")
        suspend fun getCurrentCourseDetails(
            @Query("uid")
            uid : String?
        ) : Response<CurrentCourseDetailsResponse>

        @GET("api/admission/upload")
        suspend fun getDocDetails(
            @Query("uid")
            uid : String?
        ) : Response<DocResponse>

        @GET("api/admission/facultDashboard")
        suspend fun getfacultyDashboard(
            @Query("uid")
            uid : String?
        ) : Response<FacultyDashboardResponse>

        @GET("api/admission/pendingApp")
        suspend fun getPendingApplication(
            @Query("uid")
            uid : String?
        ) : PendingApplicationResponse

        @GET("api/admission/currentEducation")
        suspend fun getEducationDetails(
            @Query("uid")
            uid : String?
        ) : Response<EducationDetailsResponse>

        @GET("api/admission/sem")
        suspend fun getSemDetails(
            @Query("uid")
            uid : String?
        ) : Response<SemDetailsResponse>

        @GET("api/admission/feeStructureStud")
        suspend fun getFeeStructure(
            @Query("uid")
            uid : String?
        ) : Response<FeeStructureResponse>

        @GET("api/admission/initiate_payment")
        suspend fun getInitiatePaymentBodyDetails(
            @Query("uid")
            uid : String?
        ) : Response<InitiatePaymentBodyResponse>
    }

}