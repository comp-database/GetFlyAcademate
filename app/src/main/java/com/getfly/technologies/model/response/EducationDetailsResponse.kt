package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class EducationDetailsResponse(
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("bio_marks")
        val bioMarks: String,
        @SerializedName("chemistry_score_in_hsc")
        val chemistryScoreInHsc: String,
        @SerializedName("dep_cgpi")
        val depCgpi: String,
        @SerializedName("dep_clg_name")
        val depClgName: String,
        @SerializedName("dep_marks")
        val depMarks: String,
        @SerializedName("dep_passing_year")
        val depPassingYear: String,
        @SerializedName("dep_per")
        val depPer: String,
        @SerializedName("dep_seat")
        val depSeat: String,
        @SerializedName("dip_board")
        val dipBoard: String,
        @SerializedName("edu_id")
        val eduId: Int,
        @SerializedName("hsc_marks")
        val hscMarks: String,
        @SerializedName("hsc_name_of_board")
        val hscNameOfBoard: String,
        @SerializedName("hsc_passing_year")
        val hscPassingYear: String,
        @SerializedName("hsc_percentage")
        val hscPercentage: String,
        @SerializedName("hsc_seat_year")
        val hscSeatYear: String,
        @SerializedName("maths_score_hsc")
        val mathsScoreHsc: String,
        @SerializedName("physics_score_in_hsc")
        val physicsScoreInHsc: String,
        @SerializedName("ssc_marks")
        val sscMarks: String,
        @SerializedName("ssc_name_of_board")
        val sscNameOfBoard: String,
        @SerializedName("ssc_passing_year")
        val sscPassingYear: String,
        @SerializedName("ssc_percentage")
        val sscPercentage: String,
        @SerializedName("ssc_seat_number")
        val sscSeatNumber: String,
        @SerializedName("stud_id")
        val studId: Int,
        @SerializedName("vocational_subject")
        val vocationalSubject: String,
        @SerializedName("vocational_total_score_hsc")
        val vocationalTotalScoreHsc: String
    )
}