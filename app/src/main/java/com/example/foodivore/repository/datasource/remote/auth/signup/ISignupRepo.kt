package com.example.foodivore.repository.datasource.remote.auth.signup

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface ISignupRepo {

    suspend fun registerWithEmailAndPassword(email: String, password: String): Resource<User.UserData?>
}