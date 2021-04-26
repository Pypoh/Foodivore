package com.example.foodivore.repository.datasource.remote.auth.signup

import com.example.foodivore.network.ApiClient
import com.example.foodivore.network.ApiServices
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class SignupRepoImpl : ISignupRepo {
    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User.SignUpResponse?> {
        return try {
            val signUpResult = ApiClient.getApiService().signup(User.SignUpRequest(email, password))

            Resource.Success(signUpResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}