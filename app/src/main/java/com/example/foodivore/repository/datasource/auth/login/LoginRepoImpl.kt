package com.example.foodivore.repository.datasource.auth.login

import android.util.Log
import com.example.foodivore.network.ApiClient
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginRepoImpl : ILoginRepo {

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User.LoginResponse?> {
        try {
//            ApiClient.getApiService().login(
//                User.LoginRequest(
//                    email = email,
//                    password = password
//                )
//            ).enqueue(object : Callback<User.LoginResponse> {
//                override fun onResponse(
//                    call: Call<User.LoginResponse>,
//                    response: Response<User.LoginResponse>
//                ) {
//                    val loginResponse = response.body()
//
//                    if (response.code() == 200) {
//
//                        Thread.sleep(5000L)
//
//                        // Create Object
//                        userData = loginResponse!!
//
//                        Log.d("LoginFragment", loginResponse.toString())
//                    } else {
//
//                    }
//                }
//
//                override fun onFailure(call: Call<User.LoginResponse>, t: Throwable) {
//                    throw Exception(t)
//                }
//
//            })

            val result = ApiClient.getApiService().login(
                User.LoginRequest(
                    email = email,
                    password = password
                )
            )

            return Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }
}