package com.example.foodivore.ui.food.domain

import com.example.foodivore.repository.datasource.remote.food.IFoodRepo
import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

class FoodImpl(private val foodRepository: IFoodRepo) : IFood {
    override suspend fun getFoodByName(name: String): Resource<List<Food.FoodResponse?>?> =
        foodRepository.getFoodByName(name)
}