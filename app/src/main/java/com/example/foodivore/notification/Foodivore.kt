package com.example.foodivore.notification

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.example.foodivore.R
import com.example.foodivore.notification.data.ReminderEntity
import com.example.foodivore.notification.notif.NotificationHelper

class Foodivore : Application(){

    companion object {
        lateinit var instance: Foodivore
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        NotificationHelper.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel.")
        NotificationHelper.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, true,
            ReminderEntity.MealType.Breakfast.name, "Notification channel for breakfast.")
        NotificationHelper.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, true,
            ReminderEntity.MealType.Lunch.name, "Notification channel for lunch.")
        NotificationHelper.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            ReminderEntity.MealType.Dinner.name, "Notification channel for dinner.")
    }
}