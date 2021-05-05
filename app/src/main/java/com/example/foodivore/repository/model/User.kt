package com.example.foodivore.repository.model

import com.google.gson.annotations.SerializedName

object User {

    data class UserData(
        @SerializedName("username")
        var username: String = "",

        @SerializedName("email")
        var email: String = "",

        @SerializedName("password")
        val password: String = ""
    )

    data class LoginRequest(
        @SerializedName("email")
        var email: String,

        @SerializedName("password")
        var password: String
    )

    data class LoginResponse(
        @SerializedName("id")
        var id: String,

        @SerializedName("email")
        var email: String,

        @SerializedName("roles")
        var roles: List<String>,

        @SerializedName("accessToken")
        var accessToken: String,

        @SerializedName("calorieNeeds")
        var calorieNeeds: Float
    )

    data class SignUpRequest(
        @SerializedName("email")
        var email: String,

        @SerializedName("password")
        var password: String
    )

    data class SignUpResponse(
        @SerializedName("id")
        var id: String,

        @SerializedName("email")
        var email: String,

        @SerializedName("accessToken")
        var accessToken: String,

        @SerializedName("calorieNeeds")
        var calorieNeeds: Float
    )

    data class PreTestData(
        @SerializedName("name")
        var name: String,

        @SerializedName("height")
        var height: String,

        @SerializedName("weight")
        var weight: String,

        @SerializedName("sex")
        var sex: String,

        @SerializedName("age")
        var age: String,

        @SerializedName("activity")
        var activity: String,

        @SerializedName("target")
        var target: String,

    )

    data class PreTestResponse(
        @SerializedName("message")
        var message: String
    )

    data class CalorieNeedsResponse(
        @SerializedName("calorieNeeds")
        var calorieNeeds: Float
    )

}