package com.example.foodivore.repository.datasource.remote.profile

import android.util.Log
import com.example.foodivore.MainActivity
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class ProfileRepoImpl : IProfileRepo {
    var userResult: User.PreTestData? = null
    override suspend fun getUserData(jwtToken: String): Resource<User.PreTestData> {
        return try {

            userResult =
                ApiClient.getUserApiService(jwtToken).getUserData()

            Resource.Success(userResult!!)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getUserCalorie(jwtToken: String): Resource<User.CalorieNeedsResponse> {
        return try {
            val calorieResult = ApiClient.getUserApiService(jwtToken).getUserCalorie()

            Resource.Success(calorieResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}