package com.example.foodivore.repository.datasource.local.data.domain

import com.example.foodivore.repository.datasource.local.data.ReminderEntity

interface IReminderDbHelper {
    suspend fun getAll(): List<ReminderEntity>

    suspend fun findById(id: Int): ReminderEntity

    suspend fun insert(vararg reminder: ReminderEntity)

    suspend fun delete(reminder: ReminderEntity)

    suspend fun update(vararg reminder: ReminderEntity)
}