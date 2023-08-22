package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class FacultyDashboardResponse(
    @SerializedName("aids")
    val aids: Int,
    @SerializedName("count_1")
    val count1: Int,
    @SerializedName("count_2")
    val count2: Int,
    @SerializedName("count_3")
    val count3: Int,
    @SerializedName("count_4")
    val count4: Int,
    @SerializedName("count_5")
    val count5: Int,
    @SerializedName("cs")
    val cs: Int,
    @SerializedName("extc")
    val extc: Int,
    @SerializedName("it")
    val it: Int
)