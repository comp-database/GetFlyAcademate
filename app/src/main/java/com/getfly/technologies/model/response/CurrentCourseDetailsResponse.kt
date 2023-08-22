package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class CurrentCourseDetailsResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("academic_year")
        val academicYear: String,
        @SerializedName("branch_id")
        val branchId: Int,
        @SerializedName("cat_id")
        val catId: Any?,
        @SerializedName("dd_id")
        val ddId: Any?,
        @SerializedName("defence_status")
        val defenceStatus: Any?,
        @SerializedName("details_of_prize")
        val detailsOfPrize: Any?,
        @SerializedName("doc_ids")
        val docIds: Int,
        @SerializedName("father_id")
        val fatherId: Int,
        @SerializedName("final_submit")
        val finalSubmit: Int,
        @SerializedName("gr_number")
        val grNumber: String,
        @SerializedName("guardian_id")
        val guardianId: Int,
        @SerializedName("HDegree")
        val hDegree: Any?,
        @SerializedName("mother_id")
        val motherId: Int,
        @SerializedName("neft_id")
        val neftId: Any?,
        @SerializedName("part_payment")
        val partPayment: Any?,
        @SerializedName("personal_details_id")
        val personalDetailsId: Int,
        @SerializedName("physically_handicap")
        val physicallyHandicap: Any?,
        @SerializedName("programm_id")
        val programmId: Int,
        @SerializedName("received_scholership")
        val receivedScholership: Any?,
        @SerializedName("seat_type_id")
        val seatTypeId: Int,
        @SerializedName("sid")
        val sid: Int,
        @SerializedName("speacial_talent")
        val speacialTalent: Any?,
        @SerializedName("stud_clg_id")
        val studClgId: String,
        @SerializedName("uid")
        val uid: Int
    )
}