package com.example.foodivore.ui.main.home.domain

import com.example.foodivore.repository.datasource.remote.plan.IPlanRepo
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.main.plans.domain.IPlan
import com.example.foodivore.utils.viewobject.Resource

class HomeImpl(private val planRepository: IPlanRepo) : IHome {

    override suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Food.FoodResponse?>?> = planRepository.getRecordByDate(authToken, time)

}