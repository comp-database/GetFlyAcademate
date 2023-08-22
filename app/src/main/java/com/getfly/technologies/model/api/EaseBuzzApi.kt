package com.getfly.technologies.model.api

import com.getfly.technologies.model.response.InitiatePaymentResponse
import com.getfly.technologies.model.response.TransactionAPIResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class EaseBuzzWebService {
    var api: EaseBuzzApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pay.easebuzz.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(EaseBuzzApi::class.java)
    }

    interface EaseBuzzApi {

        @FormUrlEncoded
        @POST("payment/initiateLink")
        suspend fun postInitiatePaymentData(
            @Field("amount")
            amount: Float,

            @Field("hash")
            hash : String ,

            @Field("txnid")
            txnid: String,

            @Field("email")
            email: String,

            @Field("firstname")
            name: String,

            @Field("phone")
            phone: String,

            @Field("productinfo")
            productinfo: String,

            @Field("surl")
            surl: String ="http://vppcoe-va.getflytechnologies.com/api/account/success_payment/",

            @Field("furl")
            furl: String = "http://vppcoe-va.getflytechnologies.com/api/account/failed_payment/",

            @Field("key")
            key : String = "ZHJ8U1ZHPB",

//            @Field("city")
//            city: String = "",
//
//            @Field("country")
//            country: String = "",
//
//            @Field("address1")
//            address1: String = "",
//
//            @Field("address2")
//            address2: String ="",
//
//            @Field("state")
//            state: String = "",
//
//            @Field("udf1")
//            udf1: String = "",
//
//            @Field("udf10")
//            udf10: String = "",
//
//            @Field("udf2")
//            udf2: String = "",
//
//            @Field("udf3")
//            udf3: String = "",
//
//            @Field("udf4")
//            udf4: String = "",
//
//            @Field("udf5")
//            udf5: String = "",
//
//            @Field("udf6")
//            udf6: String = "",
//
//            @Field("udf7")
//            udf7: String = "",
//
//            @Field("udf8")
//            udf8: String = "",
//
//            @Field("udf9")
//            udf9: String = "",
//
//            @Field("zipcode")
//            zipcode: String = ""

        ): Response<InitiatePaymentResponse.InitiatePaymentResponseSuccess>

        @FormUrlEncoded
        @POST("transaction/v1/retrieve")
        suspend fun postTransactionPaymentData(
            @Field("amount")
            amount: Float,
            @Field("txnid")
            txnid: String,
            @Field("key")
            key: String = "ZHJ8U1ZHPB",
            @Field("email")
            email: String,
            @Field("phone")
            phone: String,
        ):Response<TransactionAPIResponse>

    }
}