package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName
class InitiatePaymentResponse{
    data class InitiatePaymentResponseSuccess(
        @SerializedName("data")
        val `data`: String,
        @SerializedName("status")
        val status: Int
    )

    data class InitiatePaymentResponseError(
        @SerializedName("data")
        val `data`: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("error_desc")
        val `errorDesc`: String,
    )
}

