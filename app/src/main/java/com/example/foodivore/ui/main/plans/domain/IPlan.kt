package com.example.foodivore.ui.main.plans.domain

import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.utils.viewobject.Resource

interface IPlan {
    suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Food.FoodResponse?>?>

    suspend fun getPlanByDate(
        authToken: String,
        time: Long
    ): Resource<List<Food.FoodResponse?>?>
}