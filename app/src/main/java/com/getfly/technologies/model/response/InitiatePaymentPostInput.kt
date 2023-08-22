package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class InitiatePaymentPostInput(
    @SerializedName("address1")
    val address1: String = "",

    @SerializedName("address2")
    val address2: String ="",

    @SerializedName("amount")
    val amount: Float,

    @SerializedName("city")
    val city: String = "",

    @SerializedName("country")
    val country: String = "",

    @SerializedName("email")
    val email: String,

    @SerializedName("furl")
    val furl: String = "http://vppcoe-va.getflytechnologies.com/api/account/failed_payment/",

    @SerializedName("firstname")
    val name: String,

    @SerializedName("phone")
    val phone: Any?,

    @SerializedName("productinfo")
    val productinfo: String,

    @SerializedName("state")
    val state: String = "",

    @SerializedName("surl")
    val surl: String ="http://vppcoe-va.getflytechnologies.com/api/account/success_payment/",

    @SerializedName("txnid")
    val txnid: String,

    @SerializedName("udf1")
    val udf1: String = "",

    @SerializedName("udf10")
    val udf10: String = "",

    @SerializedName("udf2")
    val udf2: String = "",

    @SerializedName("udf3")
    val udf3: String = "",

    @SerializedName("udf4")
    val udf4: String = "",

    @SerializedName("udf5")
    val udf5: String = "",

    @SerializedName("udf6")
    val udf6: String = "",

    @SerializedName("udf7")
    val udf7: String = "",

    @SerializedName("udf8")
    val udf8: String = "",

    @SerializedName("udf9")
    val udf9: String = "",

    @SerializedName("zipcode")
    val zipcode: String = ""
)