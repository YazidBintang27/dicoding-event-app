package com.latihan.dicodingevent.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.latihan.dicodingevent.models.ListEventsModel
import com.latihan.dicodingevent.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
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

   init {
      getUpcomingEvent()
      getFinishedEvent()
   }

   private fun getUpcomingEvent() {
      _isLoading.value = true
      repository.requestUpcomingEvent().enqueue(object: Callback<ListEventsModel> {
         override fun onResponse(call: Call<ListEventsModel>, response: Response<ListEventsModel>) {
            _isLoading.value = false
            if (response.isSuccessful) {
               _upcomingEventData.value = response.body()?.listEvents?.take(5)
            } else {
               Log.e("MainViewModel", "On Failure: ${response.message()}")
            }
         }

         override fun onFailure(call: Call<ListEventsModel>, t: Throwable) {
            _isLoading.value = false
            Log.e("MainViewModel", "On Failure: ${t.message}")
         }
      })
   }

   private fun getFinishedEvent() {
      _isLoading.value = true
      repository.requestFinishedEvent().enqueue(object: Callback<ListEventsModel> {
         override fun onResponse(call: Call<ListEventsModel>, response: Response<ListEventsModel>) {
            _isLoading.value = false
            if (response.isSuccessful) {
               _finishedEventData.value = response.body()?.listEvents?.take(5)
            } else {
               Log.e("MainViewModel", "On Failure: ${response.message()}")
            }
         }

         override fun onFailure(call: Call<ListEventsModel>, t: Throwable) {
            Log.e("MainViewModel", "On Failure: ${t.message}")
         }
      })
   }
}