package com.example.foodivore.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.auth.login.domain.ILogin

class LoginVMFactory(private val useCase: ILogin) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ILogin::class.java).newInstance(useCase)
    }
}