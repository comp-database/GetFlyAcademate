package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class DocResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("docs")
    val docs: Docs
) {
    data class Docs(
        @SerializedName("aadhar_card")
        val aadharCard: String,
        @SerializedName("antiragging_form")
        val antiraggingForm: Any?,
        @SerializedName("back_passbook")
        val backPassbook: String,
        @SerializedName("cap_allotment_letter")
        val capAllotmentLetter: String,
        @SerializedName("caste_certificate")
        val casteCertificate: String,
        @SerializedName("caste_validation")
        val casteValidation: String,
        @SerializedName("college_admission_letter")
        val collegeAdmissionLetter: String,
        @SerializedName("doc_id")
        val docId: Int,
        @SerializedName("domicile")
        val domicile: String,
        @SerializedName("ews_pro")
        val ewsPro: String,
        @SerializedName("fc_center_varification")
        val fcCenterVarification: String,
        @SerializedName("fee_reciept")
        val feeReciept: String,
        @SerializedName("gap_cert")
        val gapCert: Any?,
        @SerializedName("hsc_marksheet")
        val hscMarksheet: String,
        @SerializedName("income_certificate")
        val incomeCertificate: String,
        @SerializedName("jee_score_card")
        val jeeScoreCard: String,
        @SerializedName("lc")
        val lc: String,
        @SerializedName("mht_cet_score_card")
        val mhtCetScoreCard: String,
        @SerializedName("nonCreamy")
        val nonCreamy: String,
        @SerializedName("parentSignature")
        val parentSignature: String,
        @SerializedName("photo")
        val photo: String,
        @SerializedName("ration_card")
        val rationCard: String,
        @SerializedName("signature")
        val signature: String,
        @SerializedName("ssc_marksheet")
        val sscMarksheet: String
    )
}