package com.getfly.technologies.model.response

import com.google.gson.annotations.SerializedName

data class FeeDetailsResponse (
    @SerializedName("total_paid")
    val paidAmount: Int,
    @SerializedName("total")
    val totalAmount: Int,
    @SerializedName("receipt")
    val receipt: Int
)
