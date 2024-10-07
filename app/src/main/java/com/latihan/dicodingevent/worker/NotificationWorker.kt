package com.latihan.dicodingevent.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.latihan.dicodingevent.data.remote.repository.Repository
import com.latihan.dicodingevent.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
   @Assisted private val repository: Repository,
   @Assisted appContext: Context,
   @Assisted params: WorkerParameters
): CoroutineWorker(appContext, params) {

   override suspend fun doWork(): Result {
      return try {
         val newEventData = repository.requestNotification()
         newEventData.listEvents?.forEach { events ->
            if (events?.name != null && events.endTime != null) {
               NotificationHelper.sendNotification(applicationContext, events.name, events
                  .beginTime, events.link)
            }
         }
         Result.success()
      } catch (e: Exception) {
         Result.retry()
      }
   }
}