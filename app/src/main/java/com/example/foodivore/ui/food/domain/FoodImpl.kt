package com.example.foodivore.ui.food.domain

import com.example.foodivore.repository.datasource.remote.food.IFoodRepo
import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

class FoodImpl(private val foodRepository: IFoodRepo) : IFood {
    override suspend fun getFoodByName(name: String): Resource<List<Food.FoodResponse?>?> =
        foodRepository.getFoodByName(name)

    override suspend fun getFoodBySchedule(schedule: String): Resource<List<Food.FoodResponse?>?> =
        foodRepository.getFoodBySchedule(schedule)

    override suspend fun getFoods(): Resource<List<Food.FoodResponse>?> =
        foodRepository.getFoods()
}