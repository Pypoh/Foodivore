package com.example.foodivore.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.foodivore.ui.main.MainActivity
import com.example.foodivore.R
import com.example.foodivore.repository.datasource.local.data.ReminderEntity

object NotificationHelper {

    private const val COMPLETION_REQUEST_CODE = 2021

    fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String) {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // 3
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createSampleDataNotification(context: Context, title: String, message: String,
                                     bigText: String, autoCancel: Boolean) {

        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_bell)
            setContentTitle(title)
            setContentText(message)
            setAutoCancel(autoCancel)
            setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(autoCancel)

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, notificationBuilder.build())
    }

    fun createNotificationForMeal(context: Context, reminderEntity: ReminderEntity) {

        // create a group notification
        val groupBuilder = buildGroupNotification(context, reminderEntity)

        // create the pet notification
        val notificationBuilder = buildNotificationForMeal(context, reminderEntity)

        // add an action to the pet notification
//        val administerPendingIntent = createPendingIntentForAction(context, reminderData)
//        notificationBuilder.addAction(R.drawable.baseline_done_black_24, context.getString(R.string.administer), administerPendingIntent)

        // call notify for both the group and the pet notification
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(reminderEntity.type.ordinal, groupBuilder.build())
        notificationManager.notify(reminderEntity.id, notificationBuilder.build())
    }

    private fun buildGroupNotification(context: Context, reminderEntity: ReminderEntity): NotificationCompat.Builder {
        // 1
        val channelId = "${context.packageName}-${reminderEntity.type.name}"
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_bell)
            setContentTitle(reminderEntity.type.name)
            setContentText(context.getString(R.string.group_notification_for, reminderEntity.type.name))
            setStyle(NotificationCompat.BigTextStyle()
                .bigText(context.getString(R.string.group_notification_for, reminderEntity.type.name)))
            setAutoCancel(true)
            setGroupSummary(true) // 2
            setGroup(reminderEntity.type.name) // 3
        }
    }

    private fun buildNotificationForMeal(context: Context, reminderEntity: ReminderEntity): NotificationCompat.Builder {


        val channelId = "${context.packageName}-${reminderEntity.type.name}"

        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_bell)
            setContentTitle(reminderEntity.name)
            setAutoCancel(true)

            // get a drawable reference for the LargeIcon
            val drawable = when (reminderEntity.type) {
                ReminderEntity.MealType.Breakfast -> R.drawable.ic_bell
                ReminderEntity.MealType.Lunch -> R.drawable.ic_bell
                ReminderEntity.MealType.Dinner -> R.drawable.ic_bell
                else -> R.drawable.ic_bell
            }
            setLargeIcon(BitmapFactory.decodeResource(context.resources, drawable))
            setContentText("${reminderEntity.type.name}, ${reminderEntity.desc}")
            setGroup(reminderEntity.type.name)

//            // note is not important so if it doesn't exist no big deal
//            if (reminderData.note != null) {
//                setStyle(NotificationCompat.BigTextStyle().bigText(reminderData.note))
//            }

            // Launches the app to open the reminder edit screen when tapping the whole notification
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("id", reminderEntity.id)
            }

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent)

            Log.d("NotificationHelper", "notification built with data: $reminderEntity")
        }
    }

    fun showNotificationOreo(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "default", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        with(NotificationCompat.Builder(context, "default")) {
            setSmallIcon(R.drawable.logo_only)
            setContentTitle("Custom Title")
            setContentText("Tap to start the application")
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            manager.notify(87, build())
        }
    }

    private fun createPendingIntentForAction(context: Context, reminderEntity: ReminderEntity): PendingIntent? {
        /*
            Create an Intent to update the ReminderData if Administer action is clicked
         */
        val administerIntent = Intent(context, AppGlobalReceiver::class.java).apply {
            action = context.getString(R.string.action_notify_meal_time)
            putExtra(AppGlobalReceiver.NOTIFICATION_ID, reminderEntity.id)
//            putExtra(ReminderDialog.KEY_ID, reminderData.id)
//            putExtra(ReminderDialog.KEY_ADMINISTERED, true)
        }

        return PendingIntent.getBroadcast(context, COMPLETION_REQUEST_CODE, administerIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}