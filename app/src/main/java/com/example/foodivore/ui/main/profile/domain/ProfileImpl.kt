package com.example.foodivore.ui.main.profile.domain

import com.example.foodivore.repository.datasource.remote.profile.IProfileRepo
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

class ProfileImpl(private val profileRepository: IProfileRepo) : IProfile {
    override suspend fun getUserData(jwtToken: String): Resource<User.PreTestData> =
        profileRepository.getUserData(jwtToken)

    override suspend fun getUserCalorie(jwtToken: String): Resource<User.CalorieNeedsResponse> =
        profileRepository.getUserCalorie(jwtToken)
}