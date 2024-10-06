package com.latihan.dicodingevent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.databinding.EventCardVerticalBinding
import com.latihan.dicodingevent.data.remote.models.ListEventsModel

class UpcomingEventAdapter: RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder>() {

   private var upcomingEventData: List<ListEventsModel.Events?>? = listOf()
   private lateinit var onItemClickCallback: OnItemClickCallback
   private lateinit var onFavouriteClickCallback: OnFavouriteClickCallback
   private var favouriteEvent: List<Int> = listOf()
   private var isFavourite = false

   interface OnItemClickCallback {
      fun onItemClicked(id: Int)
   }

   interface OnFavouriteClickCallback {
      fun onFavouriteClicked(isFavourite: Boolean, favouriteEventEntity: FavouriteEventEntity)
   }

   fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
      this.onItemClickCallback = onItemClickCallback
   }

   fun setOnFavouriteClickCallback(onFavouriteClickCallback: OnFavouriteClickCallback) {
      this.onFavouriteClickCallback = onFavouriteClickCallback
   }

   class ViewHolder(val binding: EventCardVerticalBinding): RecyclerView.ViewHolder(binding.root) {
      fun bind(data: ListEventsModel.Events?) {
         binding.apply {
            Glide.with(ivImage)
               .load(data?.imageLogo)
               .error(R.drawable.noimage)
               .into(ivImage)
            tvTitle.text = data?.name
            tvOwner.text = itemView.context.getString(R.string.owner_text, data?.ownerName)
            tvCategory.text = data?.category
            icFavourite.setImageResource(R.drawable.favourite_stroke_rounded)
         }
      }
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): ViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = EventCardVerticalBinding.inflate(layoutInflater, parent, false)
      return ViewHolder(binding)
   }

   override fun getItemCount(): Int {
      return upcomingEventData?.size ?: 0
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(upcomingEventData?.get(position))
      holder.itemView.setOnClickListener {
         onItemClickCallback.onItemClicked(upcomingEventData?.get(position)?.id ?: 0)
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