package com.example.foodivore.repository.datasource.remote.plan

import android.util.Log
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class PlanRepoImpl : IPlanRepo {
    override suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Food.FoodResponse?>?> {
        return try {
            val recordResult = ApiClient.getUserApiService(authToken).getRecordByDate(time)
            Log.d("PlanDebugREpo", recordResult.toString())
            Resource.Success(recordResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}