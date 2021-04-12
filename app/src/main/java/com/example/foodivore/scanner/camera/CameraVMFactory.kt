package com.example.foodivore.scanner.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.food.domain.IFood

class CameraVMFactory(private val useCase: IFood) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IFood::class.java).newInstance(useCase)
    }
}