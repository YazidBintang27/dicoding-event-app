package com.latihan.dicodingevent.utils

object ApiConstant {
   const val BASE_URL = "https://event-api.dicoding.dev"
   const val UPCOMING_ENDPOINT = "/events?active=1"
   const val FINISHED_ENDPOINT = "/events?active=0"
   const val SEARCH_ENDPOINT = "/events?active=-1"
   const val DETAIL_ENDPOINT = "/events/{id}"
   const val NOTIFICATION_ENDPOINT = "/events?active=-1&limit=1"
}