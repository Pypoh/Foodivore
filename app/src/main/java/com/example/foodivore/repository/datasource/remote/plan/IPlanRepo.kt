package com.example.foodivore.repository.datasource.remote.plan

import com.example.foodivore.repository.model.Record
import com.example.foodivore.utils.viewobject.Resource
import java.util.*

interface IPlanRepo {
    suspend fun getRecordByDate(
        authToken: String,
        time: Long
    ): Resource<List<Record.RecordResponse?>?>

}