package com.example.foodivore.ui.main.plans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.main.plans.domain.IPlan
import com.example.foodivore.ui.pretest.domain.IPreTest

class PlansVMFactory(private val useCase: IPlan) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IPlan::class.java).newInstance(useCase)
    }
}