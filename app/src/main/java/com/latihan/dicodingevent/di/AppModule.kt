package com.latihan.dicodingevent.di

import android.app.Application
import androidx.room.Room
import com.latihan.dicodingevent.data.local.room.EventDatabase
import com.latihan.dicodingevent.data.local.room.FavouriteEventDao
import com.latihan.dicodingevent.data.remote.service.ApiService
import com.latihan.dicodingevent.utils.ApiConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

   @Provides
   @Singleton
   fun provideRetrofit(): Retrofit {
      return Retrofit.Builder()
         .baseUrl(ApiConstant.BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
   }

   @Provides
   @Singleton
   fun provideService(retrofit: Retrofit): ApiService {
      return retrofit.create(ApiService::class.java)
   }

   @Provides
   @Singleton
   fun provideDatabase(app: Application): EventDatabase {
      return Room.databaseBuilder(
         app,
         EventDatabase::class.java,
         "event_db"
      ).build()
   }

   @Provides
   @Singleton
   fun provideDao(db: EventDatabase): FavouriteEventDao {
      return db.dao
   }
}