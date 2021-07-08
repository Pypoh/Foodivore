package com.example.foodivore.notification

import android.content.Context
import com.example.foodivore.repository.datasource.local.data.ReminderEntity

object DataUtils {

    private var defaultData = arrayListOf<ReminderEntity>(
        ReminderEntity(
            0,
            "Sarapan",
            ReminderEntity.MealType.Breakfast,
            "Jadwal untuk makan pagi",
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
        ), ReminderEntity(
            1,
            "Makan Siang",
            ReminderEntity.MealType.Lunch,
            "Jadwal untuk makan siang",
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
        ), ReminderEntity(
            2,
            "Makan Malam",
            ReminderEntity.MealType.Dinner,
            "Jadwal untuk makan malam",
            19,
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
        ), ReminderEntity(
            3,
            "Test Notifikasi",
            ReminderEntity.MealType.Lunch,
            "Cuma buat uji coba development notif",
            17,
            7,
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


    fun getReminderById(id: Int): ReminderEntity? {
        for (data in defaultData) {
            if (id == data.id) {
                return data
            }
        }

        return null
    }

    fun scheduleAlarmsForData(context: Context, data: List<ReminderEntity>) {
        for (reminderData in data) {
            AlarmScheduler.scheduleAlarmsForReminder(context, reminderData)
        }
    }

    fun updateAlarm(context: Context, data: ReminderEntity) {
        AlarmScheduler.updateAlarmsForReminder(context, data)
    }


    fun getDefaultData(): List<ReminderEntity> = defaultData
}