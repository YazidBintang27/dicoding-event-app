package com.latihan.dicodingevent.data.remote.repository

import com.latihan.dicodingevent.data.remote.models.DetailEventModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import retrofit2.Call

interface Repository {
   fun requestUpcomingEvent(): Call<ListEventsModel>

   fun requestFinishedEvent(): Call<ListEventsModel>

   fun requestSearchEvent(keyword: String): Call<ListEventsModel>

   fun requestDetailEvent(id: Int): Call<DetailEventModel>
}