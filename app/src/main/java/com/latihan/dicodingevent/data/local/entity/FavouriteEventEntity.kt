package com.latihan.dicodingevent.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_event")
data class FavouriteEventEntity(
   @PrimaryKey(autoGenerate = false)
   @ColumnInfo(name = "id")
   var id: Int = 0,

   @ColumnInfo(name = "name")
   var name: String = "",

   @ColumnInfo(name = "owner_name")
   var ownerName: String = "",

   @ColumnInfo(name = "category")
   var category: String = "",

   @ColumnInfo(name = "image_logo")
   var imageLogo: String = ""
)
