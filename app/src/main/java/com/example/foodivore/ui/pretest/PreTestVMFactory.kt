package com.example.foodivore.ui.pretest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.main.profile.domain.IProfile
import com.example.foodivore.ui.pretest.domain.IPreTest

class PreTestVMFactory(private val useCase: IProfile) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IProfile::class.java).newInstance(useCase)
    }
}