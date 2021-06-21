package com.example.foodivore.repository.datasource.remote.recommendation

import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

interface IRecommendationRepo {
    suspend fun getRecommendation(
        authToken: String,
        schedule: String
    ): Resource<List<Food.FoodResponse?>?>

}