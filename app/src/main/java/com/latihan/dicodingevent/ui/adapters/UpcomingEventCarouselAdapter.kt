package com.latihan.dicodingevent.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.databinding.UpcomingEventsCardCarouselBinding
import com.latihan.dicodingevent.data.remote.models.ListEventsModel

class UpcomingEventCarouselAdapter: RecyclerView.Adapter<UpcomingEventCarouselAdapter.ViewHolder>() {

   private var upcomingEventData: List<ListEventsModel.Events?>? = listOf()
   private lateinit var onUpcomingItemClickCallback: OnUpcomingItemClickCallback
   private lateinit var onFavouriteClickCallback: OnFavouriteClickCallback
   private var favouriteEvent: List<Int> = listOf()
   private var isFavourite = false

   interface OnUpcomingItemClickCallback {
      fun onItemClicked(id: Int)
   }

   interface OnFavouriteClickCallback {
      fun onFavouriteClicked(isFavourite: Boolean, favouriteEventEntity: FavouriteEventEntity)
   }

   fun setOnItemClickCallback(onItemClickCallback: OnUpcomingItemClickCallback) {
      this.onUpcomingItemClickCallback = onItemClickCallback
   }

   fun setOnFavouriteClickCallback(onFavouriteClickCallback: OnFavouriteClickCallback) {
      this.onFavouriteClickCallback = onFavouriteClickCallback
   }

   class ViewHolder(val binding: UpcomingEventsCardCarouselBinding): RecyclerView.ViewHolder(binding
      .root) {
      fun bind(data: ListEventsModel.Events?) {
         binding.apply {
            Glide.with(ivUpcomingImage)
               .load(data?.imageLogo)
               .error(R.drawable.noimage)
               .into(ivUpcomingImage)
            tvTitle.text = data?.name
            tvCategory.text = data?.category
            icFavourite.setImageResource(R.drawable.favourite_stroke_rounded)
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = UpcomingEventsCardCarouselBinding.inflate(layoutInflater, parent, false)
      return ViewHolder(binding)
   }

   override fun getItemCount(): Int {
      return upcomingEventData?.size ?: 0
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(upcomingEventData?.get(position))
      holder.itemView.setOnClickListener {
         onUpcomingItemClickCallback.onItemClicked(upcomingEventData?.get(position)?.id ?: 0)
      }
      val id = upcomingEventData?.get(position)?.id
      isFavourite = favouriteEvent.contains(id)
      var iconFavourite = if (isFavourite) {
         R.drawable.favourite_fill_rounded
      } else {
         R.drawable.favourite_stroke_rounded
      }
      holder.binding.icFavourite.setImageResource(iconFavourite)
      holder.binding.icFavourite.setOnClickListener {
         isFavourite = !isFavourite
         iconFavourite = if (isFavourite) {
            R.drawable.favourite_fill_rounded
         } else {
            R.drawable.favourite_stroke_rounded
         }
         holder.binding.icFavourite.setImageResource(iconFavourite)
         val favouriteEventEntity = FavouriteEventEntity(
            upcomingEventData?.get(position)?.id,
            upcomingEventData?.get(position)?.name,
            upcomingEventData?.get(position)?.ownerName,
            upcomingEventData?.get(position)?.category,
            upcomingEventData?.get(position)?.imageLogo
         )
         onFavouriteClickCallback.onFavouriteClicked(isFavourite, favouriteEventEntity)
      }
   }

   @SuppressLint("NotifyDataSetChanged")
   fun setData(upcomingEventData: List<ListEventsModel.Events?>?) {
      this.upcomingEventData = upcomingEventData
      notifyDataSetChanged()
   }

   @SuppressLint("NotifyDataSetChanged")
   fun setFavouriteData(favouriteEvent: List<Int>) {
      this.favouriteEvent = favouriteEvent
      notifyDataSetChanged()
   }
}