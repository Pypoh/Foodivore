package com.example.foodivore.repository.datasource.remote.pretest

import android.util.Log
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class PreTestRepoImpl : IPreTestRepo {

    override suspend fun postPreTestData(
        userPreTest: User.PreTestData,
        jwtToken: String
    ): Resource<User.DefaultResponse> {
        return try {
            Log.d("PreTestActivity", "Uploading with token $jwtToken")

            val userData =
                ApiClient.getUserApiService(jwtToken).postPreTest(
                    User.PreTestData(
                        name = userPreTest.name,
                        height = userPreTest.height,
                        weight = userPreTest.weight,
                        sex = userPreTest.sex,
                        age = userPreTest.age,
                        activity = userPreTest.activity,
                        target = userPreTest.target,
                        calorieNeeds = 0f
                    )
                )

            Resource.Success(userData)
        } catch (e: Exception) {
            Resource.Failure(e)
        }


    }
}