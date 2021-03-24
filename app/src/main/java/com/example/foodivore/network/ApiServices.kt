package com.example.foodivore.network

import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST(Constants.LOGIN_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    suspend fun loginAsync(@Body request: User.LoginRequest): User.LoginResponse

    @POST(Constants.SIGNUP_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun signup(@Body request: User.SignUpRequest): Call<User.SignUpResponse>

    @GET(Constants.FOODS_URL)
    fun fetchFoods(): Call<List<Food.FoodResponse>>

}