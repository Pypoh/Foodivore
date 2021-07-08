package com.example.foodivore.notification

import android.app.Application
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.foodivore.R
import com.example.foodivore.repository.datasource.local.data.ReminderEntity


class Foodivore : Application(){

    companion object {
        lateinit var instance: Foodivore
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH, false,
            getString(R.string.app_name), "App notification channel."
        )
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH, true,
            ReminderEntity.MealType.Breakfast.name, "Notification channel for breakfast."
        )
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH, true,
            ReminderEntity.MealType.Lunch.name, "Notification channel for lunch."
        )
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH, false,
            ReminderEntity.MealType.Dinner.name, "Notification channel for dinner."
        )

//        val br: BroadcastReceiver = AlarmReceiver()
//        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
//            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        }
//        registerReceiver(br, filter)

    }
}