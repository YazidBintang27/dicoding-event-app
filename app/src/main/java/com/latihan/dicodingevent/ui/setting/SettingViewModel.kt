package com.latihan.dicodingevent.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.latihan.dicodingevent.data.local.datastore.NotificationPreferences
import com.latihan.dicodingevent.data.local.datastore.ThemePreferences
import com.latihan.dicodingevent.worker.NotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
   private val themePreferences: ThemePreferences,
   private val notificationPreferences: NotificationPreferences,
   private val workManager: WorkManager
): ViewModel() {

   fun getThemeSetting(): LiveData<Boolean> {
      return themePreferences.getThemeSetting().asLiveData()
   }

   fun saveThemeSetting(isDarkMode: Boolean) {
      viewModelScope.launch {
         themePreferences.saveThemeSetting(isDarkMode)
      }
   }

   fun getNotificationSetting(): LiveData<Boolean> {
      return notificationPreferences.getNotificationSetting().asLiveData()
   }

   fun saveNotificationSetting(isNotificationEnabled: Boolean) {
      viewModelScope.launch {
         notificationPreferences.saveNotificationSetting(isNotificationEnabled)
         if (!isNotificationEnabled) {
            scheduleDailyNotification()
         } else {
            cancelDailyNotification()
         }
      }
   }

   private fun scheduleDailyNotification() {
      val constraints = Constraints.Builder()
         .setRequiredNetworkType(NetworkType.CONNECTED)
         .build()
      val workRequest = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 15, TimeUnit.MINUTES)
         .setConstraints(constraints)
         .build()
      workManager.enqueueUniquePeriodicWork(
         "DailyNotification",
         ExistingPeriodicWorkPolicy.REPLACE,
         workRequest
      )
   }

   private fun cancelDailyNotification() {
      workManager.cancelUniqueWork("DailyNotification")
   }
}