package com.example.foodivore.ui.setting.mealschedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.repository.datasource.local.data.domain.IReminderDbHelper

class MealScheduleVMFactory(
    private val useCaseDatabase: IReminderDbHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IReminderDbHelper::class.java)
            .newInstance(useCaseDatabase)
    }
}