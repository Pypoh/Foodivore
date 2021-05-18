package com.example.foodivore.notification.data

import android.content.Context
import androidx.room.*

@Database(entities = [ReminderEntity::class], version = 1)
@TypeConverters(ReminderConverter::class)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile private var instance: ReminderDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            ReminderDatabase::class.java, "todo-list.db")
            .build()
    }
}