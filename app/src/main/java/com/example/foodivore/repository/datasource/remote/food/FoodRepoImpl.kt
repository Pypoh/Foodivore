package com.example.foodivore.repository.datasource.remote.food

import android.util.Log
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

class FoodRepoImpl : IFoodRepo {
    override suspend fun getFoodByName(name: String): Resource<List<Food.FoodResponse?>?> {
        return try {
            val foodResult = ApiClient.getApiService().getFoodByName(name)

            Resource.Success(foodResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getFoods(): Resource<List<Food.FoodResponse>?> {
        return try {
            val foodResult = ApiClient.getApiService().getFoods()

            Resource.Success(foodResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getFoodBySchedule(schedule: String): Resource<List<Food.FoodResponse>?> {
        return try {
            val foodResult = ApiClient.getApiService().getFoodBySchedule(schedule)

            Resource.Success(foodResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


}