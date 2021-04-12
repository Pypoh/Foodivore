package com.example.foodivore.repository.datasource.remote.pretest

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface IPreTestRepo {
    suspend fun postPreTestData(userPreTest: User.PreTestData): Resource<User.PreTestResponse>
}