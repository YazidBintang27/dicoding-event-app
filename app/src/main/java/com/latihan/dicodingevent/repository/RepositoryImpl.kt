package com.latihan.dicodingevent.repository

import com.latihan.dicodingevent.models.DetailEventModel
import com.latihan.dicodingevent.models.ListEventsModel
import com.latihan.dicodingevent.service.ApiService
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