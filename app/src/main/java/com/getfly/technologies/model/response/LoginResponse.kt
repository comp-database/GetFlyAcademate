package com.getfly.technologies.model.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("isLogin")
    val isLogin: Boolean,
    @SerializedName("message")
    val SerializedName: String,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("user_type")
    val userType: Int
)