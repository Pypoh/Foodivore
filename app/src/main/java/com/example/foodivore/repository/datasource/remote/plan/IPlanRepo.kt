package com.example.foodivore.repository.datasource.remote.plan

import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource
import java.util.*

interface IPlanRepo {
    suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.RecordIngredient?>?>

    suspend fun getPlanByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.PlanResponse?>?>

    suspend fun sendPlan(
        authToken: String,
        plan: Record.PlanRequest
    ): Resource<Record.PlanResponse>

    suspend fun postRecord(
        authToken: String,
        data: Record.RecordRequest
    ): Resource<Record.RecordResponse>

    suspend fun deletePlan(
        authToken: String,
        planId: String
    ): Resource<User.DefaultResponse>
}