package com.example.foodivore.repository.datasource.remote.profile

import android.util.Log
import com.example.foodivore.MainActivity
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class ProfileRepoImpl : IProfileRepo {
    override suspend fun getUserData(jwtToken: String): Resource<User.PreTestData> {
        return try {
            Log.d("ProfileFragment", "Repos: $jwtToken")

            val userResult =
                ApiClient.getUserApiService(jwtToken).getUserData()

            Log.d("ProfileFragment", "Repos: $userResult")

            Resource.Success(userResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}