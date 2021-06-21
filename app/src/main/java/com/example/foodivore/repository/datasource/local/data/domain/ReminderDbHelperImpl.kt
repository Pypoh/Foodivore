package com.example.foodivore.repository.datasource.local.data.domain

import com.example.foodivore.repository.datasource.local.data.ReminderDatabase
import com.example.foodivore.repository.datasource.local.data.ReminderEntity

class ReminderDbHelperImpl(private val reminderDatabase: ReminderDatabase) : IReminderDbHelper {
    override suspend fun getAll(): List<ReminderEntity> = reminderDatabase.reminderDao().getAll()

    override suspend fun findById(id: Int): ReminderEntity =
        reminderDatabase.reminderDao().findById(id)

    override suspend fun insert(vararg reminder: ReminderEntity) =
        reminderDatabase.reminderDao().insert(*reminder)

    override suspend fun delete(reminder: ReminderEntity) =
        reminderDatabase.reminderDao().delete(reminder)

    override suspend fun update(vararg reminder: ReminderEntity) =
        reminderDatabase.reminderDao().update(
            *reminder
        )
}