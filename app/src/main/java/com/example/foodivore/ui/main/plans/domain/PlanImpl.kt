package com.example.foodivore.ui.main.plans.domain

import com.example.foodivore.repository.datasource.remote.plan.IPlanRepo
import com.example.foodivore.repository.model.Record
import com.example.foodivore.utils.viewobject.Resource

class PlanImpl(private val planRepository: IPlanRepo) : IPlan {
    override suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.RecordResponse?>?> =
        planRepository.getRecordByDate(authToken, time)
}