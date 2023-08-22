package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class SemDetailsResponse(
    @SerializedName("entrance")
    val entrance: List<Entrance>
) {
    data class Entrance(
        @SerializedName("aggregated_score")
        val aggregatedScore: String,
        @SerializedName("external_kt")
        val externalKt: Int,
        @SerializedName("grade_obtained")
        val gradeObtained: String,
        @SerializedName("internal_kt")
        val internalKt: Int,
        @SerializedName("sem_id")
        val semId: Int,
        @SerializedName("sem_number")
        val semNumber: Int,
        @SerializedName("stud_id")
        val studId: Int,
        @SerializedName("total_kt")
        val totalKt: Int
    )
}