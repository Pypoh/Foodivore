package com.example.foodivore.notification.data

import androidx.room.*

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder_data")
    suspend fun getAll(): List<ReminderEntity>

    @Query("SELECT * FROM reminder_data WHERE id LIKE :id")
    suspend fun findById(id: Int): ReminderEntity

    @Insert
    suspend fun insert(vararg reminder: ReminderEntity)

    @Delete
    suspend fun delete(reminder: ReminderEntity)

    @Query("DELETE FROM reminder_data")
    suspend fun deleteAll()

    @Update
    suspend fun update(vararg reminder: ReminderEntity)


}