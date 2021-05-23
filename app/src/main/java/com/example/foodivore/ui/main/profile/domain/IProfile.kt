package com.example.foodivore.ui.main.profile.domain

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface IProfile {
    suspend fun getUserData(jwtToken: String): Resource<User.PreTestData>
    suspend fun getUserCalorie(jwtToken: String): Resource<User.CalorieNeedsResponse>

    suspend fun postPreTestData(userPreTest: User.PreTestData, jwtToken: String): Resource<User.PreTestResponse>


}