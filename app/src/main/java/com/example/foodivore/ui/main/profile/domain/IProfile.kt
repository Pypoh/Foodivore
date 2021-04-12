package com.example.foodivore.ui.main.profile.domain

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface IProfile {
    suspend fun getUserData(jwtToken: String): Resource<User.PreTestData>
}