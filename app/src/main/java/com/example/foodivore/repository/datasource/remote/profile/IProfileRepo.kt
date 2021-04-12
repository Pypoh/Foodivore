package com.example.foodivore.repository.datasource.remote.profile

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface IProfileRepo {
    suspend fun getUserData(jwtToken: String): Resource<User.PreTestData>
}