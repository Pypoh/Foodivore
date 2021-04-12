package com.example.foodivore.repository.datasource.remote.food

import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

interface IFoodRepo {
    suspend fun getFoodByName(name: String): Resource<List<Food.FoodResponse?>?>
}