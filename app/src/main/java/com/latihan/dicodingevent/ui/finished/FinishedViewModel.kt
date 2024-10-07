package com.latihan.dicodingevent.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import com.latihan.dicodingevent.data.remote.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishedViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {

   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _finishedEventData = MutableLiveData(emptyList<ListEventsModel.Events?>())
   val finishedEventData: LiveData<List<ListEventsModel.Events?>?> = _finishedEventData

   private var _favouriteEventData = MutableLiveData<List<Int>>()
   val favouriteEventData: LiveData<List<Int>> = _favouriteEventData

   init {
      getFinishedEvent()
      getAllFavouriteEventById()
   }

   private fun getFinishedEvent() {
      viewModelScope.launch {
         _isLoading.value = true
         try {
            val response = repository.requestFinishedEvent().listEvents
            _finishedEventData.value = response
            _isLoading.value = false
         } catch (e: Exception) {
            Log.e("FinishedViewModel", "Error")
         }
      }
   }

   fun addFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.addFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("FinishedViewModel", "Error add favourite: ${e.message}")
         }
      }
   }

   fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.deleteFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("FinishedViewModel", "Error delete favourite: ${e.message}")
         }
      }
   }

   private fun getAllFavouriteEventById() {
      viewModelScope.launch {
         try {
            repository.getAllFavouriteEventId()
               .collect { favouriteData ->
                  _favouriteEventData.value = favouriteData
               }
         } catch (e: Exception) {
            Log.e("HomeViewModel", "Error get favourite: ${e.message}")
         }
      }
   }
}