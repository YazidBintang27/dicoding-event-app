package com.latihan.dicodingevent.ui.home

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {
   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _upcomingEventData = MutableLiveData(emptyList<ListEventsModel.Events?>())
   val upcomingEventData: LiveData<List<ListEventsModel.Events?>?> = _upcomingEventData

   private var _finishedEventData = MutableLiveData(emptyList<ListEventsModel.Events?>())
   val finishedEventData: LiveData<List<ListEventsModel.Events?>?> = _finishedEventData

   private var _favouriteEventData = MutableLiveData<List<Int>>()
   val favouriteEventData: LiveData<List<Int>> = _favouriteEventData

   init {
      getUpcomingEvent()
      getFinishedEvent()
      getAllFavouriteEventById()
   }

   private fun getUpcomingEvent() {
      viewModelScope.launch {
         _isLoading.value = true
         try {
            val response = repository.requestUpcomingEvent().listEvents
            _upcomingEventData.value = response?.take(5)
            _isLoading.value = false
         } catch (e: Exception) {
            Log.e("HomeViewModel", "Error")
         }
      }
   }

   private fun getFinishedEvent() {
      viewModelScope.launch {
         _isLoading.value = true
         try {
            val response = repository.requestFinishedEvent().listEvents
            _finishedEventData.value = response?.take(5)
            _isLoading.value = false
         } catch (e: Exception) {
            Log.e("HomeViewModel", "Error")
         }
      }
   }

   fun addFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.addFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("HomeViewModel", "Error add favourite: ${e.message}")
         }
      }
   }

   fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.deleteFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("HomeViewModel", "Error delete favourite: ${e.message}")
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