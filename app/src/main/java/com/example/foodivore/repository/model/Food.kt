package com.example.foodivore.repository.model

import com.google.gson.annotations.SerializedName

object Food {

    data class Catalogue(
        var title: String,
        var data: ArrayList<Detail>
    )

    data class Detail(
        var imageUrl: String,
        var name: String,
        var calorie: Int,
        var type: String
    )

    data class FoodResponse(
        @SerializedName("title")
        var title: String,

        @SerializedName("description")
        var description: String,

        @SerializedName("createdAt")
        var createdAt: String,

        @SerializedName("updatedAt")
        var updatedAt: String,

        @SerializedName("id")
        var id: String
    )


}