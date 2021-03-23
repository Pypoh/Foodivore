package com.example.foodivore.ui.auth.login.domain

import com.example.foodivore.repository.datasource.auth.login.ILoginRepo
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

class LoginImpl(private val loginRepository: ILoginRepo) : ILogin {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User.LoginResponse?> = loginRepository.loginWithEmailAndPassword(email, password)
}
