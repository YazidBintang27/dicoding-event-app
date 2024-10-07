package com.latihan.dicodingevent.data.remote.repository

import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.data.remote.models.DetailEventModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface Repository {
   // API Methods
   suspend fun requestUpcomingEvent(): ListEventsModel

   suspend fun requestFinishedEvent(): ListEventsModel

   suspend fun requestSearchEvent(keyword: String): ListEventsModel

   suspend fun requestDetailEvent(id: Int): DetailEventModel

   suspend fun requestNotification(): ListEventsModel

   // Room Database Methods
   suspend fun addFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   suspend fun updateFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   suspend fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   suspend fun requestFavouriteEvent(): Flow<List<FavouriteEventEntity>>

   fun getAllFavouriteEventId(): Flow<List<Int>>
}