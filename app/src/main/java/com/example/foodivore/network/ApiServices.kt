package com.example.foodivore.network

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: User.LoginRequest): Call<User.LoginResponse>

    @POST(Constants.SIGNUP_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun signup(@Body request: User.SignUpRequest): Call<User.SignUpResponse>

}