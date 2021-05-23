package com.example.foodivore.repository.datasource.remote.profile

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface IProfileRepo {
    suspend fun getUserData(jwtToken: String): Resource<User.PreTestData>
    suspend fun getUserCalorie(jwtToken: String): Resource<User.CalorieNeedsResponse>

    suspend fun postPreTestData(userPreTest: User.PreTestData, jwtToken: String): Resource<User.PreTestResponse>
}