package com.example.foodivore.ui.main.plans.domain

import com.example.foodivore.repository.datasource.remote.plan.IPlanRepo
import com.example.foodivore.repository.model.Food
import com.example.foodivore.utils.viewobject.Resource

class PlanImpl(private val planRepository: IPlanRepo) : IPlan {
    override suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Food.FoodResponse?>?> =
        planRepository.getRecordByDate(authToken, time)

    override suspend fun getPlanByDate(
        authToken: String,
        time: Long
    ): Resource<List<Food.FoodResponse?>?> =
        planRepository.getPlanByDate(authToken, time)
}