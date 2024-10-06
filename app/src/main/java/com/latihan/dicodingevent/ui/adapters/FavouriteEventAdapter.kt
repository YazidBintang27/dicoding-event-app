package com.latihan.dicodingevent.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.databinding.EventCardVerticalBinding

class FavouriteEventAdapter: RecyclerView.Adapter<FavouriteEventAdapter.ViewHolder>() {

   private var listFavouriteEventData: List<FavouriteEventEntity> = listOf()
   private lateinit var onItemClickCallback: OnItemClickCallback
   private lateinit var onFavouriteClickCallback: OnFavouriteClickCallBack

   interface OnItemClickCallback {
      fun onItemClicked(id: Int)
   }

   interface OnFavouriteClickCallBack {
      fun onFavouriteClicked(isFavourite: Boolean, favouriteEventEntity: FavouriteEventEntity)
   }

   fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
      this.onItemClickCallback = onItemClickCallback
   }

   fun setOnFavouriteClickCallback(onFavouriteClickCallback: OnFavouriteClickCallBack) {
      this.onFavouriteClickCallback = onFavouriteClickCallback
   }

   class ViewHolder(val binding: EventCardVerticalBinding): RecyclerView.ViewHolder
      (binding.root) {
      fun bind(data: FavouriteEventEntity) {
         binding.apply {
            Glide.with(ivImage)
               .load(data.imageLogo)
               .error(R.drawable.noimage)
               .into(ivImage)
            tvTitle.text = data.name
            tvOwner.text = itemView.context.getString(R.string.owner_text, data.ownerName)
            tvCategory.text = data.category
            icFavourite.setImageResource(R.drawable.favourite_fill_rounded)
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = EventCardVerticalBinding.inflate(layoutInflater, parent, false)
      return ViewHolder(binding)
   }

   override fun getItemCount(): Int {
      return listFavouriteEventData.size
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(listFavouriteEventData[position])
      holder.itemView.setOnClickListener {
         onItemClickCallback.onItemClicked(listFavouriteEventData[position].id ?: 0)
      }
      holder.binding.icFavourite.setOnClickListener {
         var isFavourite = listFavouriteEventData[position].isFavourite
         isFavourite = !isFavourite
         val iconFavourite = if (isFavourite) {
            R.drawable.favourite_fill_rounded
         } else {
            R.drawable.favourite_stroke_rounded
         }
         Log.d("FavouriteAdapter", "is favourite: $isFavourite")
         holder.binding.icFavourite.setImageResource(iconFavourite)
         onFavouriteClickCallback.onFavouriteClicked(isFavourite, listFavouriteEventData[position])
      }
   }

   @SuppressLint("NotifyDataSetChanged")
   fun setData(listFavouriteEventData: List<FavouriteEventEntity>) {
      this.listFavouriteEventData = listFavouriteEventData
      notifyDataSetChanged()
   }
}