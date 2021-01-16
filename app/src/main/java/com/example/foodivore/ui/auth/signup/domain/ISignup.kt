package com.example.foodivore.ui.auth.signup.domain

import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface ISignup {
    suspend fun signupWithEmailAndPassword(email: String, password: String) : Resource<User.UserData?>
}