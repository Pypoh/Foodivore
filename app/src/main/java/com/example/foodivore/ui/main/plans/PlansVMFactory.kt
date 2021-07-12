package com.example.foodivore.ui.main.plans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.repository.datasource.local.data.domain.IReminderDbHelper
import com.example.foodivore.ui.main.plans.domain.IPlan
import com.example.foodivore.ui.pretest.domain.IPreTest

class PlansVMFactory(private val useCase: IPlan, private val useCaseDatabase: IReminderDbHelper) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IPlan::class.java, IReminderDbHelper::class.java)
            .newInstance(useCase, useCaseDatabase)
    }
}