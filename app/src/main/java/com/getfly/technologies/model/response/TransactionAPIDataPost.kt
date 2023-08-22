package com.getfly.technologies.model.response

data class TransactionAPIDataPost(
    val txnid : String,
    val key : String,
    val amount : Float,
    val email : String,
    val phone : String,
    val hash : String,
)

