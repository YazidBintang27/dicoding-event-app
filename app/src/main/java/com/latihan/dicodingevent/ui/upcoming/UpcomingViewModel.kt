package com.latihan.dicodingevent.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import com.latihan.dicodingevent.data.remote.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
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

   init {
      getUpcomingEvent()
   }

   private fun getUpcomingEvent() {
      _isLoading.value = true
      repository.requestUpcomingEvent().enqueue(object: Callback<ListEventsModel> {
         override fun onResponse(call: Call<ListEventsModel>, response: Response<ListEventsModel>) {
            _isLoading.value = false
            if (response.isSuccessful) {
               _upcomingEventData.value = response.body()?.listEvents
            } else {
               Log.e("UpcomingViewModel", "On Failure: ${response.message()}")
            }
         }

         override fun onFailure(call: Call<ListEventsModel>, t: Throwable) {
            _isLoading.value = false
            Log.e("UpcomingViewModel", "On Failure: ${t.message}")
         }
      })
   }
}