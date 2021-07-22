package com.example.foodivore.repository.datasource.remote.recommendation

import android.util.Log
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class RecommendationRepoImpl : IRecommendationRepo {
    override suspend fun getRecommendation(
        authToken: String,
        schedule: String
    ): Resource<List<Food.FoodResponse?>?> {
        return try {
            val recommendationResult =
                ApiClient.getUserApiService(authToken).getRecommendation(schedule)
            Log.d("RecActivityRepo", recommendationResult.toString())
            Resource.Success(recommendationResult)
        } catch (e: Exception) {
            Log.d("RecActivityRepo", e.message!!)
            Resource.Failure(e)
        }
    }

    override suspend fun getSchedule(schedule: String): Resource<List<Food.Schedule>> {
        return try {
            val scheduleResult = ApiClient.getApiService().getSchedule(schedule)
            Resource.Success(scheduleResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}