package com.example.foodivore.ui.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.food.domain.IFood
import com.example.foodivore.ui.recommendation.domain.IRecommendation

class RecommendationVMFactory(
    private val useCase: IRecommendation,
    private val useCaseFood: IFood
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IRecommendation::class.java, IFood::class.java)
            .newInstance(useCase, useCaseFood)
    }
}