package com.example.foodivore.ui.food.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.food.domain.IFood

class FoodCatalogueVMFactory(private val useCaseFood: IFood) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IFood::class.java).newInstance(useCaseFood)
    }
}