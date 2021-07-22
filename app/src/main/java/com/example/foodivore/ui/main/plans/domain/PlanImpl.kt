package com.example.foodivore.ui.main.plans.domain

import com.example.foodivore.repository.datasource.remote.plan.IPlanRepo
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

class PlanImpl(private val planRepository: IPlanRepo) : IPlan {
    override suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.RecordIngredient?>?> =
        planRepository.getRecordByDate(authToken, time)

    override suspend fun getPlanByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.PlanResponse?>?> =
        planRepository.getPlanByDate(authToken, time)

    override suspend fun sendPlan(
        authToken: String,
        plan: Record.PlanRequest
    ): Resource<Record.PlanResponse> = planRepository.sendPlan(authToken, plan)

    override suspend fun postRecord(
        authToken: String,
        data: Record.RecordRequest
    ): Resource<Record.RecordResponse> = planRepository.postRecord(authToken, data)

    override suspend fun deletePlan(
        authToken: String,
        planId: String
    ): Resource<User.DefaultResponse> = planRepository.deletePlan(authToken, planId)
}