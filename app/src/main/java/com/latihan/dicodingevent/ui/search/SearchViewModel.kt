package com.latihan.dicodingevent.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SearchViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {
   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _searchEventData = MutableLiveData(emptyList<ListEventsModel.Events?>())
   val searchEventData: LiveData<List<ListEventsModel.Events?>?> = _searchEventData

   fun searchData(keyword: String) {
      viewModelScope.launch {
         _isLoading.value = true
         try {
            val response = repository.requestSearchEvent(keyword).listEvents
            _searchEventData.value = response
            _isLoading.value = false
         } catch (e: Exception) {
            Log.e("HomeViewModel", "Error")
         }
      }
   }
}