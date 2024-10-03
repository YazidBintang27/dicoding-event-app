package com.latihan.dicodingevent.data.remote.service

import com.latihan.dicodingevent.data.remote.models.DetailEventModel
import com.latihan.dicodingevent.data.remote.models.ListEventsModel
import com.latihan.dicodingevent.utils.ApiConstant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET(ApiConstant.UPCOMING_ENDPOINT)
   suspend fun getUpcomingEventList(): ListEventsModel

   @GET(ApiConstant.FINISHED_ENDPOINT)
   suspend fun getFinishedEventList(): ListEventsModel

   @GET(ApiConstant.DETAIL_ENDPOINT)
   suspend fun getDetailEventById(@Path("id") id: Int): DetailEventModel

   @GET(ApiConstant.SEARCH_ENDPOINT)
   suspend fun getSearchEventByKeyword(@Query("q") keyword: String): ListEventsModel
}