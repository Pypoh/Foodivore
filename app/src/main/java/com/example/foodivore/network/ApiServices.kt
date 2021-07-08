package com.example.foodivore.network

import com.example.foodivore.repository.model.Article
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.Constants
import okhttp3.MultipartBody
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
    suspend fun postPreTest(@Body request: User.PreTestData): User.DefaultResponse

    @Multipart
    @POST(Constants.USER_URL + "/image")
    suspend fun uploadProfileImage(@Part file: MultipartBody.Part): User.DefaultResponse

    @GET(Constants.USER_URL)
    suspend fun getUserData(): User.PreTestData

    @GET(Constants.FOODS_URL)
    suspend fun getFoods(): List<Food.FoodResponse>?

    @GET(Constants.FOODS_URL)
    suspend fun getFoodByName(@Query("name") name: String): List<Food.FoodResponse?>

    @GET(Constants.FOODS_URL)
    fun getFoodByNameAsync(@Query("name") name: String): Call<List<Food.FoodResponse?>>

    @GET(Constants.FOODS_URL)
    suspend fun getFoodBySchedule(@Query("schedule") schedule: String): List<Food.FoodResponse>?

    @GET(Constants.INGREDIENT_URL)
    suspend fun getIngredients(): List<Food.IngredientResponse>?

    @GET(Constants.INGREDIENT_URL)
    suspend fun getIngredientByType(@Query("type") type: String): List<Food.IngredientResponse>?

    @GET(Constants.RECORD_URL)
    suspend fun getRecordByDate(@Query("time") time: Long): List<Food.FoodResponse?>

    @POST(Constants.RECORD_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun postRecord(@Body request: Record.RecordRequest): Call<Record.RecordResponse>

    @GET(Constants.CALORIE_URL)
    suspend fun getUserCalorie(): User.CalorieNeedsResponse

    @GET(Constants.ARTICLE_URL + "/category")
    suspend fun getArticleCategory(): List<Article.Category>

    @GET(Constants.ARTICLE_URL)
    suspend fun getArticleByCategory(@Query("category") category: String): List<Article.Post>

    @GET(Constants.ARTICLE_URL)
    suspend fun getArticleByTitle(@Query("title") title: String): List<Article.Post>

    @GET(Constants.RECOMMENDATION_URL)
    suspend fun getRecommendation(@Query("schedule") schedule: String): List<Food.FoodResponse>?


}