package com.getfly.technologies.model.response

import com.google.gson.annotations.SerializedName

data class LoginInput(
    val email : String,
    val pass : String
)