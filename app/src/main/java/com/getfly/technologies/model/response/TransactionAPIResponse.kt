package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class TransactionAPIResponse(
    @SerializedName("msg")
    val msg: Msg,
    @SerializedName("status")
    val status: Boolean
) {
    data class Msg(
        @SerializedName("addedon")
        val addedon: String,
        @SerializedName("amount")
        val amount: String,
        @SerializedName("bank_name")
        val bankName: String,
        @SerializedName("bank_ref_num")
        val bankRefNum: String,
        @SerializedName("bankcode")
        val bankcode: String,
        @SerializedName("cardCategory")
        val cardCategory: String,
        @SerializedName("card_type")
        val cardType: String,
        @SerializedName("cardnum")
        val cardnum: String,
        @SerializedName("cash_back_percentage")
        val cashBackPercentage: String,
        @SerializedName("deduction_percentage")
        val deductionPercentage: String,
        @SerializedName("easepayid")
        val easepayid: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("error")
        val error: String,
        @SerializedName("error_Message")
        val errorMessage: String,
        @SerializedName("firstname")
        val firstname: String,
        @SerializedName("furl")
        val furl: String,
        @SerializedName("hash")
        val hash: String,
        @SerializedName("issuing_bank")
        val issuingBank: String,
        @SerializedName("key")
        val key: String,
        @SerializedName("merchant_logo")
        val merchantLogo: String,
        @SerializedName("mode")
        val mode: String,
        @SerializedName("name_on_card")
        val nameOnCard: String,
        @SerializedName("net_amount_debit")
        val netAmountDebit: String,
        @SerializedName("PG_TYPE")
        val pGTYPE: String,
        @SerializedName("payment_source")
        val paymentSource: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("productinfo")
        val productinfo: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("surl")
        val surl: String,
        @SerializedName("txnid")
        val txnid: String,
        @SerializedName("udf1")
        val udf1: String,
        @SerializedName("udf10")
        val udf10: String,
        @SerializedName("udf2")
        val udf2: String,
        @SerializedName("udf3")
        val udf3: String,
        @SerializedName("udf4")
        val udf4: String,
        @SerializedName("udf5")
        val udf5: String,
        @SerializedName("udf6")
        val udf6: String,
        @SerializedName("udf7")
        val udf7: String,
        @SerializedName("udf8")
        val udf8: String,
        @SerializedName("udf9")
        val udf9: String,
        @SerializedName("unmappedstatus")
        val unmappedstatus: String,
        @SerializedName("upi_va")
        val upiVa: String
    )
}