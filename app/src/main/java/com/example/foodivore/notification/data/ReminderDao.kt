package com.example.foodivore.notification.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface ReminderDao {

    @Query("SELECT * FROM reminder_data")
    fun getAll(): List<ReminderEntity>

    @Query("SELECT * FROM reminder_data WHERE id LIKE :id")
    fun findById(id: Int): ReminderEntity

    @Insert
    fun insert(vararg reminder: ReminderEntity)

    @Delete
    fun delete(reminder: ReminderEntity)

    @Query("DELETE FROM reminder_data")
    fun deleteAll()

    @Update
    fun update(vararg reminder: ReminderEntity)


}