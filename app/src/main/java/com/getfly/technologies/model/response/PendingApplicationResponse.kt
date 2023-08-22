package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

class PendingApplicationResponse : ArrayList<PendingApplicationResponse.PendingApplicationResponseItem>() {
    data class PendingApplicationResponseItem(
        @SerializedName("paids")
        val paids: Int,
        @SerializedName("pcs")
        val pcs: Int,
        @SerializedName("pextc")
        val pextc: Int,
        @SerializedName("pit")
        val pit: Int
    )
}