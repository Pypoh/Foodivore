package com.example.foodivore.repository.datasource.remote.plan

import android.util.Log
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class PlanRepoImpl : IPlanRepo {
    override suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.RecordIngredient?>?> {
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

    override suspend fun sendPlan(
        authToken: String,
        plan: Record.PlanRequest
    ): Resource<Record.PlanResponse> {
        return try {
            val planResult = ApiClient.getUserApiService(authToken).postPlan(plan)
            Resource.Success(planResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun postRecord(
        authToken: String,
        data: Record.RecordRequest
    ): Resource<Record.RecordResponse> {
        return try {
            val recordResult = ApiClient.getUserApiService(authToken).postRecordAsync(data)
            Resource.Success(recordResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deletePlan(
        authToken: String,
        planId: String
    ): Resource<User.DefaultResponse> {
        return try {
            val planResult = ApiClient.getUserApiService(authToken).deletePlan(planId)
            Resource.Success(planResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}