package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class FeeStructureResponse(
    @SerializedName("final_submit")
    val finalSubmit: Int,
    @SerializedName("found")
    val found: Boolean,
    @SerializedName("result")
    val result: List<Result>
) {
    data class Result(
        @SerializedName("academic_year")
        val academicYear: Int,
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("asso_id")
        val assoId: Int,
        @SerializedName("cat_id")
        val catId: Int,
        @SerializedName("fh_id")
        val fhId: Int,
        @SerializedName("passing_year")
        val passingYear: String,
        @SerializedName("program_id")
        val programId: Int
    )
}