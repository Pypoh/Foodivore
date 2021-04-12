package com.example.foodivore.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val jwtToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
//        requestBuilder.addHeader("Authorization", "Bearer $jwtToken")
        requestBuilder.addHeader("x-access-token", jwtToken)

        return chain.proceed(requestBuilder.build())
    }
}