package com.latihan.dicodingevent.data.remote.repository

import com.latihan.dicodingevent.data.remote.models.DetailEventModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import retrofit2.Call

interface Repository {
   suspend fun requestUpcomingEvent(): ListEventsModel

   suspend fun requestFinishedEvent(): ListEventsModel

   suspend fun requestSearchEvent(keyword: String): ListEventsModel

   suspend fun requestDetailEvent(id: Int): DetailEventModel
}