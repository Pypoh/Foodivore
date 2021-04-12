package com.example.foodivore.repository.datasource.remote.pretest

import com.example.foodivore.network.ApiClient
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.pretest.PreTestActivity
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class PreTestRepoImpl : IPreTestRepo {

    override suspend fun postPreTestData(userPreTest: User.PreTestData): Resource<User.PreTestResponse> {
        return try {
            val userData =
                ApiClient.getUserApiService(PreTestActivity().applicationContext).postPreTest(
                    User.PreTestData(
                        name = userPreTest.name,
                        height = userPreTest.height,
                        weight = userPreTest.weight,
                        sex = userPreTest.sex,
                        age = userPreTest.age,
                        activity = userPreTest.activity,
                        target = userPreTest.target
                    )
                )

            Resource.Success(userData)
        } catch (e: Exception) {
            Resource.Failure(e)
        }


    }
}