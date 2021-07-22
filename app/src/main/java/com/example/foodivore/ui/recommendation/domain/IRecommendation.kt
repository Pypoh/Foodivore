package com.example.foodivore.ui.recommendation.domain

import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

interface IRecommendation {
    suspend fun getRecommendation(
        authToken: String,
        schedule: String
    ): Resource<List<Food.FoodResponse?>?>

    suspend fun getSchedule(
        schedule: String
    ): Resource<List<Food.Schedule>>
}