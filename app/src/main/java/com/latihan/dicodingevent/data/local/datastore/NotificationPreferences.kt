package com.latihan.dicodingevent.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationPreferences @Inject constructor(@ApplicationContext private val context: Context) {
   private val Context.datastore by preferencesDataStore("notification_settings")

   companion object {
      val NOTIFICATION_ENABLED_KEY = booleanPreferencesKey("notification_enabled")
   }

   fun getNotificationSetting(): Flow<Boolean> {
      return context.datastore.data.map { preferences ->
         preferences[NOTIFICATION_ENABLED_KEY] ?: false
      }
   }

   suspend fun saveNotificationSetting(isNotificationEnabled: Boolean) {
      context.datastore.edit { preferences ->
         preferences[NOTIFICATION_ENABLED_KEY] = isNotificationEnabled
      }
   }
}