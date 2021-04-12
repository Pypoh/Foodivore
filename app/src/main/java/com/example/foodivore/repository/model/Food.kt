package com.example.foodivore.repository.model

import com.google.gson.annotations.SerializedName

object  Food {

    data class Catalogue(
        var backgroundColor: Int,
        var image: Int,
        var title: String,
        var example: String,
        var calorie: String,
        var data: ArrayList<Detail>
    )

    data class Detail(
        @SerializedName("imageUrl")
        var imageUrl: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("calorie")
        var calorie: Int,

        @SerializedName("type")
        var type: String
    )

    data class FoodResponse(
        @SerializedName("imageUrl")
        var imageUrl: String,

        @SerializedName("name")
        var title: String,

        @SerializedName("calorie")
        var calorie: Int,

        @SerializedName("fat")
        var fat: Float,

        @SerializedName("carb")
        var carb: Float,

        @SerializedName("prot")
        var prot: Float,

        @SerializedName("type")
        var type: String,

        @SerializedName("createdAt")
        var createdAt: String,

        @SerializedName("updatedAt")
        var updatedAt: String,

        @SerializedName("id")
        var id: String
    )


}