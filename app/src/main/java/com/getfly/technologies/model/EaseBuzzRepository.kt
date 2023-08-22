package com.getfly.technologies.model

import com.getfly.technologies.model.api.EaseBuzzWebService
import com.getfly.technologies.model.response.InitiatePaymentResponse
import com.getfly.technologies.model.response.TransactionAPIResponse
import retrofit2.Response

class EaseBuzzRepository(private val webService: EaseBuzzWebService = EaseBuzzWebService()) {
    suspend fun postDataToInitiatePayment(
        amount: Float,
        txnid: String,
        email: String,
        name: String,
        phone: String,
        productinfo: String,
        hash : String
    ): Response<InitiatePaymentResponse.InitiatePaymentResponseSuccess> {
       // Log.d("ResponsePOST",postDataToInitiatePayment(amount, txnid, email, name, phone.toString() , productinfo, hash).toString())
        return webService.api.postInitiatePaymentData(
            amount,
            txnid,
            email,
            name,
            phone ,
            productinfo,
            hash
        )
    }
    suspend fun postTransactionPaymentData(
        txnid: String,
        email: String,
        phone: String,
        amount: Float,
        hash: String,
    ):Response<TransactionAPIResponse> {
        return webService.api.postTransactionPaymentData(
            amount,
            txnid,
            email,
            phone ,
            hash
        )
    }
}