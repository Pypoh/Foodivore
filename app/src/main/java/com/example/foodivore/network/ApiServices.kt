package com.example.foodivore.network

import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.Constants
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST(Constants.LOGIN_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    suspend fun loginAsync(@Body request: User.LoginRequest): User.LoginResponse

    @POST(Constants.SIGNUP_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    suspend fun signup(@Body request: User.SignUpRequest): User.SignUpResponse

    @POST(Constants.SIGNUP_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun signupSync(@Body request: User.SignUpRequest): Call<User.SignUpResponse>

    @GET(Constants.FOODS_URL)
    fun fetchFoods(): Call<List<Food.FoodResponse>>

    @POST(Constants.USER_URL + "/pretest/update")
    @Headers("Content-Type: application/json;charset=UTF-8")
    suspend fun postPreTest(@Body request: User.PreTestData): User.PreTestResponse

    @GET(Constants.USER_URL)
    suspend fun getUserData(): User.PreTestData

    @GET(Constants.FOODS_URL)
    fun getFoodByName(@Query("name") name: String): Call<List<Food.FoodResponse?>>

    @GET(Constants.RECORD_URL)
    suspend fun getRecordByDate(@Query("time") time: Long): List<Food.FoodResponse?>

    @POST(Constants.RECORD_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun postRecord(@Body request: Record.RecordRequest): Call<Record.RecordResponse>

    @GET(Constants.CALORIE_URL)
    suspend fun getUserCalorie(): User.CalorieNeedsResponse



}