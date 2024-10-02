package com.latihan.dicodingevent.data.remote.repository

import com.latihan.dicodingevent.data.remote.models.DetailEventModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import com.latihan.dicodingevent.data.remote.service.ApiService
import retrofit2.Call
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
   private val apiService: ApiService
): Repository {
   override fun requestUpcomingEvent(): Call<ListEventsModel> {
      return apiService.getUpcomingEventList()
   }

   override fun requestFinishedEvent(): Call<ListEventsModel> {
      return apiService.getFinishedEventList()
   }

   override fun requestSearchEvent(keyword: String): Call<ListEventsModel> {
      return apiService.getSearchEventByKeyword(keyword)
   }

   override fun requestDetailEvent(id: Int): Call<DetailEventModel> {
      return apiService.getDetailEventById(id)
   }

}