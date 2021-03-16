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
        @SerializedName("status_code")
        var statusCode: Int,

        @SerializedName("auth_token")
        var authToken: String,

        @SerializedName("user")
        var user: User
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