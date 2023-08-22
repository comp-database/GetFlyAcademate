package com.getfly.technologies.model.response

import com.google.gson.annotations.SerializedName

data class InitiatePaymentBodyResponse(
    @SerializedName("ay")
    val ay: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("found")
    val found: Boolean,
    @SerializedName("result")
    val result: Int,
    @SerializedName("student")
    val student: String,
    @SerializedName("txnid")
    val txnid: String
)