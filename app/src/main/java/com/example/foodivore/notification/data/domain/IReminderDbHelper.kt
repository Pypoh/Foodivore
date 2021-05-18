package com.example.foodivore.notification.data.domain

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.foodivore.notification.data.ReminderEntity

interface IReminderDbHelper {
    suspend fun getAll(): List<ReminderEntity>

    suspend fun findById(id: Int): ReminderEntity

    suspend fun insert(vararg reminder: ReminderEntity)

    suspend fun delete(reminder: ReminderEntity)

    suspend fun update(vararg reminder: ReminderEntity)
}