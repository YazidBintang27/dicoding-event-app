package com.latihan.dicodingevent.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.data.remote.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {
   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _favouriteEventData = MutableLiveData<List<FavouriteEventEntity>>()
   val favouriteEventData: LiveData<List<FavouriteEventEntity>> = _favouriteEventData

   init {
      getFavouriteEvent()
   }

   private fun getFavouriteEvent() {
      viewModelScope.launch {
         _isLoading.value = true
         try {
            repository.requestFavouriteEvent()
               .collect { favouriteEvents ->
                  _favouriteEventData.value = favouriteEvents
                  _isLoading.value = false
               }
            _isLoading.value = false
         } catch (e: Exception) {
            Log.e("FavouriteViewModel", "Error")
         }
      }
   }

   fun addFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.addFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("FavouriteViewModel", "Error")
         }
      }
   }

   fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.deleteFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("FavouriteViewModel", "Error")
         }
      }
   }
}