package com.latihan.dicodingevent.ui.search

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
class SearchViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {
   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _searchEventData = MutableLiveData(emptyList<ListEventsModel.Events?>())
   val searchEventData: LiveData<List<ListEventsModel.Events?>?> = _searchEventData

   fun searchData(keyword: String) {
      _isLoading.value = true
      repository.requestSearchEvent(keyword).enqueue(object: Callback<ListEventsModel> {
         override fun onResponse(call: Call<ListEventsModel>, response: Response<ListEventsModel>) {
            if (response.isSuccessful) {
               _searchEventData.value = response.body()?.listEvents
            } else {
               Log.e("SearchViewModel", "On Failure: ${response.message()}")
            }
            _isLoading.value = false
         }

         override fun onFailure(call: Call<ListEventsModel>, t: Throwable) {
            Log.e("SearchViewModel", "On Failure: ${t.message}")
         }
      })
   }
}