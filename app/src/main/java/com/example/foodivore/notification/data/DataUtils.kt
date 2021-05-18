package com.example.foodivore.notification.data

import android.content.Context
import com.example.foodivore.notification.notif.AlarmScheduler

object DataUtils {

    private var defaultData = arrayListOf<ReminderEntity>(
        ReminderEntity(
            0,
            "Sarapan",
            ReminderEntity.MealType.Breakfast,
            "Jadwal untuk makan pagi",
            8,
            0,
            arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
            false
        ), ReminderEntity(
            1,
            "Makan Siang",
            ReminderEntity.MealType.Lunch,
            "Jadwal untuk makan siang",
            12,
            30,
            arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
            false
        ), ReminderEntity(
            2,
            "Makan Malam",
            ReminderEntity.MealType.Dinner,
            "Jadwal untuk makan malam",
            19,
            0,
            arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
            false
        ), ReminderEntity(
            3,
            "Test Notifikasi",
            ReminderEntity.MealType.Lunch,
            "Cuma buat uji coba development notif",
            16,
            19,
            arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
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

    fun getDefaultData(): List<ReminderEntity> = defaultData
}