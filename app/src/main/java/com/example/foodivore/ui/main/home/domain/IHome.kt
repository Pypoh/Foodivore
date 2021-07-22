package com.example.foodivore.ui.main.home.domain

import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.utils.viewobject.Resource

interface IHome {
    suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.RecordIngredient?>?>
}