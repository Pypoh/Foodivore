package com.example.foodivore.notification.data

import android.content.Context

object SampleDataHelper {

    fun loadSampleData(context: Context): List<ReminderEntity>? {

        val sampleData = arrayListOf<ReminderEntity>()
        sampleData.add(
            ReminderEntity(
                0,
                "Sarapan",
                ReminderEntity.MealType.Breakfast,
                "Ini notifikasi untuk sarapan",
                8,
                0,
                arrayListOf(
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                    "Sunday"
                ),
                false
            )
        )

        sampleData.add(
            ReminderEntity(
                1,
                "Makan Siang",
                ReminderEntity.MealType.Lunch,
                "Ini notifikasi untuk makan siang",
                12,
                30,
                arrayListOf(
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                    "Sunday"
                ),
                false
            )
        )

        sampleData.add(
            ReminderEntity(
                2,
                "Makan Malam",
                ReminderEntity.MealType.Dinner,
                "Ini notifikasi untuk makan malam",
                7,
                0,
                arrayListOf(
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                    "Sunday"
                ),
                false
            )
        )


        return sampleData

    }
}