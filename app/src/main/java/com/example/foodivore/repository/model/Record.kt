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

        @SerializedName("updatedAt")
        var updatedAt: String,

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

    data class PlanRequest(
        @SerializedName("consumedAt")
        var consumedAt: String,

        @SerializedName("ingredient")
        var ingredient: ArrayList<String> = arrayListOf(),
    )

    data class PlanResponse(
        @SerializedName("ingredient")
        var ingredient: ArrayList<IngredientPlan> = arrayListOf(),

        @SerializedName("userId")
        var userId: String,

        @SerializedName("consumedAt")
        var consumedAt: String,

        @SerializedName("createdAt")
        var createdAt: String,

        @SerializedName("updatedAt")
        var updatedAt: String,

        @SerializedName("id")
        var id: String
    )

    data class ColorLegend(
        @SerializedName("colorId")
        var colorId: Int,

        @SerializedName("name")
        var name: String
    )

    data class IngredientPlan(
        @SerializedName("name")
        var name: String,

        @SerializedName("id")
        var id: String,

        @SerializedName("calorie")
        var calorie: Float,
    )

}