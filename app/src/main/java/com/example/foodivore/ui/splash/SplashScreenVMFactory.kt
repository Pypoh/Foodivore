package com.example.foodivore.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.auth.domain.IAuth

class SplashScreenVMFactory(private val useCase: IAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IAuth::class.java).newInstance(useCase)
    }
}