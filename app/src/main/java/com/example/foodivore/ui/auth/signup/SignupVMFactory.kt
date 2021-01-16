package com.example.foodivore.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.auth.signup.domain.ISignup

class SignupVMFactory(private val useCase: ISignup) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ISignup::class.java).newInstance(useCase)
    }
}