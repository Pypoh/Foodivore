package com.example.foodivore.ui.pretest.domain

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface IPreTest {
    suspend fun postPreTestData(userPreTest: User.PreTestData, jwtToken: String): Resource<User.DefaultResponse>
}