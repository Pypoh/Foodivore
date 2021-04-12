package com.example.foodivore.ui.pretest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.pretest.domain.IPreTest

class PreTestVMFactory(private val useCase: IPreTest) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IPreTest::class.java).newInstance(useCase)
    }
}