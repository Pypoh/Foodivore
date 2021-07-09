package com.example.foodivore.repository.model

import com.google.gson.annotations.SerializedName

object Record {

    data class RecordResponse(
        @SerializedName("userId")
        var userId: String,

        @SerializedName("foodId")
        var foodId: String,

        @SerializedName("consumedAt")
        var consumedAt: String,

        @SerializedName("createdAt")
        var createdAt: String,

        @SerializedName("updated")
        var updated: String,

        @SerializedName("id")
        var id: String,

        @SerializedName("ingredient")
        var ingredient: ArrayList<String> = arrayListOf()
    )

    data class RecordRequest(
        @SerializedName("foodId")
        var foodId: String,

        @SerializedName("schedule")
        var type: String
    )


}