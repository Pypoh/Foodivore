package com.example.foodivore.repository.datasource.auth.login

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface ILoginRepo {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Resource<User.UserData?>
}