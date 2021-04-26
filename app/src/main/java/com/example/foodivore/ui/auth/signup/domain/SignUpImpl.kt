package com.example.foodivore.ui.auth.signup.domain

import com.example.foodivore.repository.datasource.remote.auth.signup.ISignupRepo
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

class SignUpImpl(private val signUpRepository: ISignupRepo) : ISignup {
    override suspend fun signupWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User.SignUpResponse?> =
        signUpRepository.registerWithEmailAndPassword(email, password)
}