package com.getfly.technologies.model.response

import com.google.gson.annotations.SerializedName


data class PersonalDetailsResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("radd")
    val radd: List<Radd>
) {
    data class Radd(
        @SerializedName("aadhar_number")
        val aadharNumber: String,
        @SerializedName("bank_account_number")
        val bankAccountNumber: String,
        @SerializedName("bank_name")
        val bankName: String,
        @SerializedName("caste")
        val caste: String,
        @SerializedName("child_number")
        val childNumber: String,
        @SerializedName("communitee")
        val communitee: String,
        @SerializedName("contact")
        val contact: String,
        @SerializedName("dob")
        val dob: String,
        @SerializedName("email")
        val email: Any?,
        @SerializedName("gender_id")
        val genderId: Int,
        @SerializedName("guardian_relation")
        val guardianRelation: Any?,
        @SerializedName("is_parent_have_domicile")
        val isParentHaveDomicile: Any?,
        @SerializedName("landline_number")
        val landlineNumber: Any?,
        @SerializedName("last_college_attended")
        val lastCollegeAttended: String,
        @SerializedName("married_status")
        val marriedStatus: String,
        @SerializedName("minority")
        val minority: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("nationality")
        val nationality: String,
        @SerializedName("padd_id")
        val paddId: Int,
        @SerializedName("personal_id")
        val personalId: Int,
        @SerializedName("place_of_birth")
        val placeOfBirth: String,
        @SerializedName("radd_id")
        val raddId: Int,
        @SerializedName("religion")
        val religion: String,
        @SerializedName("stud_id")
        val studId: Int,
        @SerializedName("sub_caste")
        val subCaste: String
    )
}