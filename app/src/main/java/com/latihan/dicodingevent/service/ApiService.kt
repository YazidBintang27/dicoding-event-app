package com.latihan.dicodingevent.service

import com.latihan.dicodingevent.models.DetailEventModel
import com.latihan.dicodingevent.models.ListEventsModel
import com.latihan.dicodingevent.util.ApiConstant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET(ApiConstant.UPCOMING_ENDPOINT)
   fun getUpcomingEventList(): Call<ListEventsModel>

   @GET(ApiConstant.FINISHED_ENDPOINT)
   fun getFinishedEventList(): Call<ListEventsModel>

   @GET(ApiConstant.DETAIL_ENDPOINT)
   fun getDetailEventById(@Path("id") id: Int): Call<DetailEventModel>

   @GET(ApiConstant.SEARCH_ENDPOINT)
   fun getSearchEventByKeyword(@Query("q") keyword: String): Call<ListEventsModel>
}