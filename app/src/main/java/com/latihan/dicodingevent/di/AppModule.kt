package com.latihan.dicodingevent.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.latihan.dicodingevent.data.local.datastore.NotificationPreferences
import com.latihan.dicodingevent.data.local.datastore.ThemePreferences
import com.latihan.dicodingevent.data.local.room.EventDatabase
import com.latihan.dicodingevent.data.local.room.FavouriteEventDao
import com.latihan.dicodingevent.data.remote.service.ApiService
import com.latihan.dicodingevent.utils.ApiConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
      ).fallbackToDestructiveMigration().build()
   }

   @Provides
   @Singleton
   fun provideDao(db: EventDatabase): FavouriteEventDao {
      return db.dao
   }

   @Provides
   @Singleton
   fun provideThemePreferences(@ApplicationContext context: Context): ThemePreferences {
      return ThemePreferences(context)
   }

   @Provides
   @Singleton
   fun provideNotificationPreferences(@ApplicationContext context: Context): NotificationPreferences {
      return NotificationPreferences(context)
   }

   @Provides
   @Singleton
   fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
      return WorkManager.getInstance(context)
   }
}