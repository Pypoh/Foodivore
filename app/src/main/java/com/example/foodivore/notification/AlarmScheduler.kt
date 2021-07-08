package com.example.foodivore.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.foodivore.R
import com.example.foodivore.repository.datasource.local.data.ReminderEntity
import java.text.SimpleDateFormat
import java.util.*

object AlarmScheduler {

    fun scheduleAlarmsForReminder(context: Context, reminderEntity: ReminderEntity) {

        // get the AlarmManager reference
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Schedule the alarms based on the days to administer the medicine
        val days = context.resources.getStringArray(R.array.days)
        if (reminderEntity.days != null) {
            for (index in reminderEntity.days!!.indices) {

                val day = reminderEntity.days!![index]
                if (day != null) {

                    // get the PendingIntent for the alarm
                    val alarmIntent = createPendingIntent(context, reminderEntity, day)

                    // schedule the alarm
                    val dayOfWeek = getDayOfWeek(days, day)
                    scheduleAlarm(reminderEntity, dayOfWeek, alarmIntent, alarmMgr)
                }
            }
        }
    }

    private fun scheduleAlarm(
        reminderEntity: ReminderEntity,
        dayOfWeek: Int,
        alarmIntent: PendingIntent?,
        alarmMgr: AlarmManager
    ) {

        try {
            // Set up the time to schedule the alarm
            val locale = Locale("in", "ID")
            val datetimeToAlarm = Calendar.getInstance(locale)
//        val datetimeToAlarm = Calendar.getInstance()

//           val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", locale)
//           val output: String = sdf.format(datetimeToAlarm)
//           Log.d("AlarmManager", "date parsed: $output")

            datetimeToAlarm.timeInMillis = System.currentTimeMillis()
            datetimeToAlarm.set(Calendar.HOUR_OF_DAY, reminderEntity.hour)
            datetimeToAlarm.set(Calendar.MINUTE, reminderEntity.minute)
            datetimeToAlarm.set(Calendar.SECOND, 0)
            datetimeToAlarm.set(Calendar.MILLISECOND, 0)
            datetimeToAlarm.set(Calendar.DAY_OF_WEEK, dayOfWeek)

            Log.d(
                "NotificationDebug",
                "scheduled alarm for ${reminderEntity.hour} hours and ${reminderEntity.minute} min at $dayOfWeek"
            )

            // Compare the datetimeToAlarm to today
            val today = Calendar.getInstance(locale)
            if (shouldNotifyToday(dayOfWeek, today, datetimeToAlarm)) {

                Log.d(
                    "AlarmManager",
                    "notified for today, ${datetimeToAlarm.time}"
                )

                try {
                    // schedule for today
                    alarmMgr.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        datetimeToAlarm.timeInMillis,
                        (1000 * 60 * 60 * 24 * 7).toLong(),
                        alarmIntent
                    )

                } catch (e: Exception) {
                    Log.d(
                        "AlarmManager",
                        "error: ${e.message}"
                    )
                }

                return
            }

            // schedule 1 week out from the day
            datetimeToAlarm.roll(Calendar.WEEK_OF_YEAR, 1)
            alarmMgr.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent
            )
        } catch (e: Exception) {
            Log.d(
                "AlarmManager",
                "error: ${e.message}"
            )
        }
    }

    private fun shouldNotifyToday(
        dayOfWeek: Int,
        today: Calendar,
        datetimeToAlarm: Calendar
    ): Boolean {
        return dayOfWeek == today.get(Calendar.DAY_OF_WEEK) &&
                today.get(Calendar.HOUR_OF_DAY) <= datetimeToAlarm.get(Calendar.HOUR_OF_DAY) &&
                today.get(Calendar.MINUTE) <= datetimeToAlarm.get(Calendar.MINUTE)
    }

    private fun createPendingIntent(
        context: Context,
        reminderEntity: ReminderEntity,
        day: String?
    ): PendingIntent? {
        // create the intent using a unique type
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            action = context.getString(R.string.action_notify_meal_time)
            type = "$day-${reminderEntity.name}-${reminderEntity.type.name}"
            putExtra("id", reminderEntity.id)
        }

        Log.d(
            "NotificationDebug",
            "pending intent created with reminder id {${reminderEntity.id}}"
        )

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun updateAlarmsForReminder(context: Context, reminderData: ReminderEntity) {
        try {
            removeAlarmsForReminder(context, reminderData)

            scheduleAlarmsForReminder(context, reminderData)
        } catch (e: Exception) {

        }
    }

    fun removeAlarmsForReminder(context: Context, reminderData: ReminderEntity) {
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java)
        intent.action = context.getString(R.string.action_notify_meal_time)
        intent.putExtra("id", reminderData.id)

        // type must be unique so Intent.filterEquals passes the check to make distinct PendingIntents
        // Schedule the alarms based on the days to administer the medicine
        if (reminderData.days != null) {
            for (i in reminderData.days!!.indices) {
                val day = reminderData.days!![i]

                if (day != null) {
                    val type = String.format(
                        Locale.getDefault(),
                        "%s-%s-%s",
                        day,
                        reminderData.name,
                        reminderData.type.name
                    )

                    intent.type = type
                    val alarmIntent = PendingIntent.getBroadcast(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    val alarmMgr =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmMgr.cancel(alarmIntent)
                }
            }
        }
    }


    private fun getDayOfWeek(days: Array<String>, dayOfWeek: String): Int {
        return when {
            dayOfWeek.equals(days[0], ignoreCase = true) -> Calendar.SUNDAY
            dayOfWeek.equals(days[1], ignoreCase = true) -> Calendar.MONDAY
            dayOfWeek.equals(days[2], ignoreCase = true) -> Calendar.TUESDAY
            dayOfWeek.equals(days[3], ignoreCase = true) -> Calendar.WEDNESDAY
            dayOfWeek.equals(days[4], ignoreCase = true) -> Calendar.THURSDAY
            dayOfWeek.equals(days[5], ignoreCase = true) -> Calendar.FRIDAY
            else -> Calendar.SATURDAY
        }
    }
}