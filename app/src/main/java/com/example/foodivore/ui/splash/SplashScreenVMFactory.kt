package com.example.foodivore.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.repository.datasource.local.data.domain.IReminderDbHelper
import com.example.foodivore.ui.auth.domain.IAuth

class SplashScreenVMFactory(
    private val useCase: IAuth,
    private val useCaseDatabase: IReminderDbHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IAuth::class.java, IReminderDbHelper::class.java)
            .newInstance(useCase, useCaseDatabase)
    }
}