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

        @SerializedName("schedule")
        var schedule: String
    )

    data class FoodResponse(
        @SerializedName("imageUrl")
        var imageUrl: String,

        @SerializedName("name")
        var title: String,

        @SerializedName("calorie")
        var calorie: Float,

        @SerializedName("fat")
        var fat: Float,

        @SerializedName("carb")
        var carb: Float,

        @SerializedName("prot")
        var prot: Float,

        @SerializedName("schedule")
        var schedule: Schedule,

        @SerializedName("createdAt")
        var createdAt: String,

        @SerializedName("updatedAt")
        var updatedAt: String,

        @SerializedName("id")
        var id: String
    )

    

    data class Schedule(
        @SerializedName("_id")
        var _id: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("maxPercentage")
        var maxPercentage: Float,

        @SerializedName("minPercentage")
        var minPercentage: Float,
    )

    data class IngredientResponse(
        @SerializedName("imageUrl")
        var imageUrl: String,

        @SerializedName("name")
        var title: String,

        @SerializedName("calorie")
        var calorie: Float,

        @SerializedName("fat")
        var fat: Float,

        @SerializedName("carb")
        var carb: Float,

        @SerializedName("prot")
        var prot: Float,

        @SerializedName("foodtype")
        var foodtype: FoodType,

        @SerializedName("createdAt")
        var createdAt: String,

        @SerializedName("updatedAt")
        var updatedAt: String,

        @SerializedName("id")
        var id: String,

        var selected: Boolean = false
    )

    data class FoodType(
        @SerializedName("_id")
        var _id: String,

        @SerializedName("name")
        var name: String,
    )

/*
    data class Menu(
        @SerializedName("schedule")
        var schedule: String,

        @SerializedName("foods")
        var food: List<FoodResponse>
    )*/

}