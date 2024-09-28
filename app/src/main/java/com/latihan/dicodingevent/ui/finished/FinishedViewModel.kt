package com.latihan.dicodingevent.ui.finished

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
class FinishedViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {

   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _finishedEventData = MutableLiveData(emptyList<ListEventsModel.Events?>())
   val finishedEventData: LiveData<List<ListEventsModel.Events?>?> = _finishedEventData

   init {
      getFinishedEvent()
   }

   private fun getFinishedEvent() {
      _isLoading.value = true
      repository.requestFinishedEvent().enqueue(object: Callback<ListEventsModel> {
         override fun onResponse(call: Call<ListEventsModel>, response: Response<ListEventsModel>) {
            _isLoading.value = false
            if (response.isSuccessful) {
               _finishedEventData.value = response.body()?.listEvents
            } else {
               Log.e("FinishedViewModel", "On Failure: ${response.message()}")
            }
         }

         override fun onFailure(call: Call<ListEventsModel>, t: Throwable) {
            Log.e("FinishedViewModel", "On Failure: ${t.message}")
         }
      })
   }
}