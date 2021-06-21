package com.example.foodivore.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.repository.datasource.local.data.domain.IReminderDbHelper
import com.example.foodivore.ui.food.domain.IFood
import com.example.foodivore.ui.main.home.domain.IHome

class HomeVMFactory(private val useCase: IHome, private val useCaseDatabase: IReminderDbHelper, private val useCaseFood: IFood) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IHome::class.java, IReminderDbHelper::class.java, IFood::class.java)
            .newInstance(useCase, useCaseDatabase, useCaseFood)
    }
}