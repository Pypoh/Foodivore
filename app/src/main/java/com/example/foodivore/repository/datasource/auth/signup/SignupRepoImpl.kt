package com.example.foodivore.repository.datasource.auth.signup

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class SignupRepoImpl : ISignupRepo {
    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User.UserData?> {
        return try {
            // Dummy User
            val data = User.UserData(email, password)

            /* Return Success Dummy Data, later will change into JSON parser because the server will
            response with JSON from API request */
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}