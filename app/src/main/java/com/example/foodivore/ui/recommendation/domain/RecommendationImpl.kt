package com.example.foodivore.ui.recommendation.domain

import com.example.foodivore.repository.datasource.remote.recommendation.IRecommendationRepo
import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

class RecommendationImpl(private val useCase: IRecommendationRepo) : IRecommendation {
    override suspend fun getRecommendation(
        authToken: String,
        schedule: String
    ): Resource<List<Food.FoodResponse?>?> = useCase.getRecommendation(authToken, schedule)
}