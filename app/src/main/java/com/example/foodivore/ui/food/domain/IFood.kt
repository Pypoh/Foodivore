package com.example.foodivore.ui.food.domain

import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

interface IFood {
    suspend fun getFoodByName(name: String): Resource<List<Food.FoodResponse?>?>
    suspend fun getFoodBySchedule(schedule: String): Resource<List<Food.FoodResponse?>?>
    suspend fun getFoods(): Resource<List<Food.FoodResponse>?>

}