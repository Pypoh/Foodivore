package com.example.foodivore.repository.datasource.auth.login

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

class LoginRepoImpl : ILoginRepo {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User.UserData?> {
        TODO("Not yet implemented")
    }
}