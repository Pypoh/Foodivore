package com.example.foodivore.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.auth.login.domain.ILogin
import com.example.foodivore.ui.main.profile.domain.IProfile

class LoginVMFactory(private val useCaseLogin: ILogin, private val useCaseProfile: IProfile) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ILogin::class.java, IProfile::class.java).newInstance(useCaseLogin, useCaseProfile)
    }
}