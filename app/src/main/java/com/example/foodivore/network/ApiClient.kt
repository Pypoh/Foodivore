package com.example.foodivore.network

import android.content.Context
import com.example.foodivore.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var apiService: ApiServices? = null
    private var userApiService: ApiServices? = null

//    fun getUserApiService(context: Context?): ApiServices {
//
//        // Initialize ApiService if not initialized yet
//        if (!::apiService.isInitialized) {
//            val retrofit = Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okhttpClient(context!!))
//                .build()
//
//            apiService = retrofit.create(ApiServices::class.java)
//        }
//
//        return apiService
//    }

    fun getUserApiService(jwtToken: String?): ApiServices {

        // Initialize ApiService if not initialized yet
        if (userApiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(jwtToken!!))
                .build()

            userApiService = retrofit.create(ApiServices::class.java)
        }

        return userApiService!!
    }

    fun getApiService(): ApiServices {

        // Initialize ApiService if not initialized yet
        if (apiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiServices::class.java)
        }

        return apiService!!
    }

    fun removeInitializedApiServices() {
        apiService = null
        userApiService = null
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(context))
            .build()
    }

    private fun okhttpClient(jwtToken: String?): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(jwtToken!!))
            .build()
    }

}