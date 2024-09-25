package com.latihan.dicodingevent.util

object ApiConstant {
   const val BASE_URL = "https://event-api.dicoding.dev"
   const val UPCOMING_ENDPOINT = "https://event-api.dicoding.dev/events?active=1"
   const val SEARCH_ENDPOINT = "https://event-api.dicoding.dev/events?active=-1&q={keyword}"
   const val DETAIL_ENDPOINT = "https://event-api.dicoding.dev/events/{id} "
}