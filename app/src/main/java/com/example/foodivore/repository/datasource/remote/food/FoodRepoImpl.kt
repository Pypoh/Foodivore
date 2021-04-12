package com.example.foodivore.repository.datasource.remote.food

import android.util.Log
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

class FoodRepoImpl : IFoodRepo{
    override suspend fun getFoodByName(name: String): Resource<List<Food.FoodResponse?>?> {
//        return try {
//
//            Log.d("CameraActivityFetch", "Fetched")
//            val foodResult = ApiClient.getApiService().getFoodByName(name)
//
//            Resource.Success(foodResult)
//        } catch (e: Exception) {
//            Log.d("CameraActivityFetch", "${e.message}")
//            Resource.Failure(e)
//        }
        return Resource.Loading()
    }
}