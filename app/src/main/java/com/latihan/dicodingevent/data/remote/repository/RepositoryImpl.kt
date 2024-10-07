package com.latihan.dicodingevent.data.remote.repository

import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.data.local.room.FavouriteEventDao
import com.latihan.dicodingevent.data.remote.models.DetailEventModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import com.latihan.dicodingevent.data.remote.service.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
   private val apiService: ApiService,
   private val dao: FavouriteEventDao
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

   override suspend fun requestNotification(): ListEventsModel {
      return apiService.getNotification()
   }

   override suspend fun addFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      dao.insertFavouriteEvent(favouriteEventEntity)
   }

   override suspend fun updateFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      dao.updateFavouriteEvent(favouriteEventEntity)
   }

   override suspend fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity) {
      dao.deleteFavouriteEvent(favouriteEventEntity)
   }

   override suspend fun requestFavouriteEvent(): Flow<List<FavouriteEventEntity>> {
      return dao.getAllFavouriteEvent()
   }

   override fun getAllFavouriteEventId(): Flow<List<Int>> {
      return dao.getAllFavouriteEventId()
   }
}