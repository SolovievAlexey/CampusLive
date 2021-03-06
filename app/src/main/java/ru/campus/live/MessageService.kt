package ru.campus.live

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.campus.live.presentation.MainActivity

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 25.05.2022 22:48
 */

private const val CHANNEL_ID = 8473943.toString()

class MessageService : FirebaseMessagingService() {

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if((message.data["type"]?.toInt() ?: 0) != 2) return

        val sPref: SharedPreferences =
            applicationContext.getSharedPreferences("AppDB", Context.MODE_PRIVATE)
        val uid = sPref.getInt("UID", 0)
        if(uid == (message.data["author"]?.toInt() ?: 0)) return

        createNotificationChannel()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_body))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.notification_body)))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val sPref: SharedPreferences =
            getSharedPreferences("AppDB", Context.MODE_PRIVATE)
        sPref.edit().putString("firebase_token", token).apply()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val sPref: SharedPreferences =
                getSharedPreferences("AppDB", Context.MODE_PRIVATE)
            val createNotificationChannel = sPref.getBoolean("createNotificationChannel", false)
            if (createNotificationChannel) return
            sPref.edit().putBoolean("createNotificationChannel", true).apply()
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}