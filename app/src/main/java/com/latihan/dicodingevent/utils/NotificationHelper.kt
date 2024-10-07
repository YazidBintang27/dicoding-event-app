package com.latihan.dicodingevent.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.latihan.dicodingevent.R
import java.text.SimpleDateFormat
import java.util.Locale

object NotificationHelper {

   private const val NOTIFICATION_ID = 1
   private const val CHANNEL_ID = "daily_notification_channel"
   private const val CHANNEL_NAME = "Daily Notifications"

   fun sendNotification(context: Context, title: String?, message: String?, linkEvent: String?) {

      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkEvent))
      val pendingIntent = PendingIntent.getActivity(
         context,
         0,
         intent,
         PendingIntent.FLAG_IMMUTABLE
      )

      val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
      val outputFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
      val date = message?.let { inputFormat.parse(it) }
      val formattedDate = date?.let { outputFormat.format(it) }
      val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      val builder = NotificationCompat.Builder(context, CHANNEL_ID)
         .setContentTitle(title)
         .setSmallIcon(R.drawable.dicodinglogocircle)
         .setContentText(context.getString(R.string.notification_message, formattedDate))
         .setContentIntent(pendingIntent)
         .setPriority(NotificationCompat.PRIORITY_DEFAULT)
         .setAutoCancel(true)

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
         )
         builder.setChannelId(CHANNEL_ID)
         notificationManager.createNotificationChannel(channel)
      }

      val notification = builder.build()
      notificationManager.notify(NOTIFICATION_ID, notification)
   }
}
