package com.latihan.dicodingevent.data.remote.repository

import com.latihan.dicodingevent.data.remote.models.DetailEventModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import com.latihan.dicodingevent.data.remote.service.ApiService
import retrofit2.Call
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
   private val apiService: ApiService
): Repository {
   override suspend fun requestUpcomingEvent(): ListEventsModel {
      return apiService.getUpcomingEventList()
   }

   override suspend fun requestFinishedEvent(): ListEventsModel {
      return apiService.getFinishedEventList()
   }

   override suspend fun requestSearchEvent(keyword: String): ListEventsModel {
      return apiService.getSearchEventByKeyword(keyword)
   }

   override suspend fun requestDetailEvent(id: Int): DetailEventModel {
      return apiService.getDetailEventById(id)
   }

}