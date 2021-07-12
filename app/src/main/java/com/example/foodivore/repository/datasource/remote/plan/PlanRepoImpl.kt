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
            Log.d("PlanDebugREpo", e.message!!)
            Resource.Failure(e)

        }
    }

    override suspend fun getPlanByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.PlanResponse?>?> {
        return try {
            val planResult = ApiClient.getUserApiService(authToken).getPlanByDate(time)
            Resource.Success(planResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun sendPlan(authToken: String, plan: Record.PlanRequest): Resource<Record.PlanResponse> {
        return try {
            val planResult = ApiClient.getUserApiService(authToken).postPlan(plan)
            Resource.Success(planResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}