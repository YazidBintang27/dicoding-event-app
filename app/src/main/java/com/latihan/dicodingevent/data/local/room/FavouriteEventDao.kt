package com.latihan.dicodingevent.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteEventDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   @Update
   suspend fun updateFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   @Delete
   suspend fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   @Query("SELECT * FROM favourite_event")
   fun getAllFavouriteEvent(): Flow<List<FavouriteEventEntity>>

   @Query("SELECT id FROM favourite_event WHERE is_favourite = 1")
   fun getAllFavouriteEventId(): Flow<List<Int>>
}