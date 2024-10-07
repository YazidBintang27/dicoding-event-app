package com.latihan.dicodingevent

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.latihan.dicodingevent.data.remote.repository.Repository
import com.latihan.dicodingevent.worker.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application(), Configuration.Provider {

   @Inject lateinit var workerFactory: NotificationWorkerFactory

   override val workManagerConfiguration: Configuration
      get() = Configuration.Builder()
         .setWorkerFactory(workerFactory)
         .build()

}

class NotificationWorkerFactory @Inject constructor(private val repository: Repository): WorkerFactory() {
   override fun createWorker(
      appContext: Context,
      workerClassName: String,
      workerParameters: WorkerParameters
   ): ListenableWorker = NotificationWorker(repository, appContext, workerParameters)
}