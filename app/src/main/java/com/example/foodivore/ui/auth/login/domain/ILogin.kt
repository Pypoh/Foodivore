package com.example.foodivore.ui.auth.login.domain

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface ILogin {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<User.LoginResponse?>
}
