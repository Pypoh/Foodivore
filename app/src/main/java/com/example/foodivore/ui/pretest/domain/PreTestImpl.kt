package com.example.foodivore.ui.pretest.domain

import com.example.foodivore.repository.datasource.remote.pretest.IPreTestRepo
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

class PreTestImpl(private val preTestRepository: IPreTestRepo) : IPreTest {
    override suspend fun postPreTestData(userPreTest: User.PreTestData): Resource<User.PreTestResponse> =
        preTestRepository.postPreTestData(userPreTest)
}