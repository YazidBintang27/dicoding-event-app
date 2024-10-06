package com.latihan.dicodingevent.ui.upcoming

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
class UpcomingViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {

   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _upcomingEventData = MutableLiveData(emptyList<ListEventsModel.Events?>())
   val upcomingEventData: LiveData<List<ListEventsModel.Events?>?> = _upcomingEventData

   private var _favouriteEventData = MutableLiveData<List<Int>>()
   val favouriteEventData: LiveData<List<Int>> = _favouriteEventData

   init {
      getUpcomingEvent()
      getAllFavouriteEventById()
   }

   private fun getUpcomingEvent() {
      viewModelScope.launch {
         _isLoading.value = true
         try {
            val response = repository.requestUpcomingEvent().listEvents
            _upcomingEventData.value = response
            _isLoading.value = false
         } catch (e: Exception) {
            Log.e("UpcomingViewModel", "Error")
         }
      }
   }

   fun addFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.addFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("UpcomingViewModel", "Error add favourite: ${e.message}")
         }
      }
   }

   fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      viewModelScope.launch(Dispatchers.IO) {
         try {
            repository.deleteFavouriteEvent(favouriteEventEntity)
         } catch (e: Exception) {
            Log.e("UpcomingViewModel", "Error delete favourite: ${e.message}")
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
            Log.e("UpcomingViewModel", "Error get favourite: ${e.message}")
         }
      }
   }
}