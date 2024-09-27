package com.latihan.dicodingevent.repository

import com.latihan.dicodingevent.models.DetailEventModel
import com.latihan.dicodingevent.models.ListEventsModel
import retrofit2.Call

interface Repository {
   fun requestUpcomingEvent(): Call<ListEventsModel>

   fun requestFinishedEvent(): Call<ListEventsModel>

   fun requestSearchEvent(keyword: String): Call<ListEventsModel>

   fun requestDetailEvent(id: Int): Call<DetailEventModel>
}