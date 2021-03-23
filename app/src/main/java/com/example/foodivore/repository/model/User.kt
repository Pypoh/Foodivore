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
    )

    data class SignUpRequest(
        @SerializedName("email")
        var email: String,

        @SerializedName("password")
        var password: String
    )

    data class SignUpResponse(
        @SerializedName("status_code")
        var statusCode: Int,

        @SerializedName("message")
        var message: String
    )

}