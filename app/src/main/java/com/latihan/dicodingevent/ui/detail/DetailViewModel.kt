package com.latihan.dicodingevent.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.latihan.dicodingevent.models.DetailEventModel
import com.latihan.dicodingevent.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
   private val repository: Repository
): ViewModel() {
   private var _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   private var _detailEventData = MutableLiveData<DetailEventModel.Event?>()
   val detailEventModel: LiveData<DetailEventModel.Event?> = _detailEventData

   fun getEventById(id: Int) {
      _isLoading.value = true
      repository.requestDetailEvent(id).enqueue(object: Callback<DetailEventModel> {
         override fun onResponse(
            call: Call<DetailEventModel>,
            response: Response<DetailEventModel>
         ) {
            _isLoading.value = false
            _detailEventData.value = response.body()?.event
         }

         override fun onFailure(call: Call<DetailEventModel>, t: Throwable) {
            Log.e("DetailViewModel", "On Failure: ${t.message}")
         }
      })
   }
}