package com.latihan.dicodingevent.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences @Inject constructor(@ApplicationContext private val context: Context){
   private val Context.datastore by preferencesDataStore("settings")

   companion object {
      val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
   }

   fun getThemeSetting(): Flow<Boolean> {
      return context.datastore.data.map { preferences ->
         preferences[DARK_MODE_KEY] ?: false
      }
   }

   suspend fun saveThemeSetting(isDarkMode: Boolean) {
      context.datastore.edit { preferences ->
         preferences[DARK_MODE_KEY] = isDarkMode
      }
   }
}