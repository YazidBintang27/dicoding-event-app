package com.latihan.dicodingevent.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity

@Dao
interface FavouriteEventDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   @Update
   suspend fun updateFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   @Delete
   suspend fun deleteFavouriteEvent(favouriteEventEntity: FavouriteEventEntity)

   @Query("SELECT * FROM favourite_event")
   suspend fun getAllFavouriteEvent(): List<FavouriteEventEntity>
}