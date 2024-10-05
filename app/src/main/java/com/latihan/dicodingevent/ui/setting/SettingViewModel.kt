package com.latihan.dicodingevent.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.latihan.dicodingevent.data.local.datastore.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
   private val themePreferences: ThemePreferences
): ViewModel() {

   fun getThemeSetting(): LiveData<Boolean> {
      return themePreferences.getThemeSetting().asLiveData()
   }

   fun saveThemeSetting(isDarkMode: Boolean) {
      viewModelScope.launch {
         themePreferences.saveThemeSetting(isDarkMode)
      }
   }
}