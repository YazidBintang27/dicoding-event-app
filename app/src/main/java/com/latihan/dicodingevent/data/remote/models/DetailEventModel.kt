package com.latihan.dicodingevent.data.remote.models


import com.google.gson.annotations.SerializedName

data class DetailEventModel(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("event")
    val event: Event?,
    @SerializedName("message")
    val message: String?
) {
    data class Event(
        @SerializedName("beginTime")
        val beginTime: String?,
        @SerializedName("category")
        val category: String?,
        @SerializedName("cityName")
        val cityName: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("endTime")
        val endTime: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("imageLogo")
        val imageLogo: String?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("mediaCover")
        val mediaCover: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("ownerName")
        val ownerName: String?,
        @SerializedName("quota")
        val quota: Int?,
        @SerializedName("registrants")
        val registrants: Int?,
        @SerializedName("summary")
        val summary: String?
    )
}