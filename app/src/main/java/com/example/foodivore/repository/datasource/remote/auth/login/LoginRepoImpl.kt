package com.example.foodivore.repository.datasource.remote.auth.login

import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.lang.Exception

class LoginRepoImpl : ILoginRepo {

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User.LoginResponse?> {
        return try {
            val userResult = ApiClient.getApiService().loginAsync(
                User.LoginRequest(
                    email = email,
                    password = password
                )
            )

            Resource.Success(userResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}