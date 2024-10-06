package com.latihan.dicodingevent.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity

@Database(entities = [FavouriteEventEntity::class], version = 3, exportSchema = false)
abstract class EventDatabase: RoomDatabase() {
   abstract val dao: FavouriteEventDao
}