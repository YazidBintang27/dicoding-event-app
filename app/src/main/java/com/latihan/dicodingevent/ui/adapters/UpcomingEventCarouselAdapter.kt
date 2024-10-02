package com.latihan.dicodingevent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.databinding.UpcomingEventsCardCarouselBinding
import com.latihan.dicodingevent.data.remote.models.ListEventsModel

class UpcomingEventCarouselAdapter: RecyclerView.Adapter<UpcomingEventCarouselAdapter.ViewHolder>() {

   private var upcomingEventData: List<ListEventsModel.Events?>? = listOf()
   private lateinit var onUpcomingItemClickCallback: OnUpcomingItemClickCallback

   interface OnUpcomingItemClickCallback {
      fun onItemClicked(id: Int)
   }

   fun setOnItemClickCallback(onItemClickCallback: OnUpcomingItemClickCallback) {
      this.onUpcomingItemClickCallback = onItemClickCallback
   }

   class ViewHolder(private val binding: UpcomingEventsCardCarouselBinding): RecyclerView.ViewHolder(binding
      .root) {
      fun bind(data: ListEventsModel.Events?) {
         binding.apply {
            Glide.with(ivUpcomingImage)
               .load(data?.imageLogo)
               .error(R.drawable.noimage)
               .into(ivUpcomingImage)
            tvTitle.text = data?.name
            tvCategory.text = data?.category
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
   }

   @SuppressLint("NotifyDataSetChanged")
   fun setData(upcomingEventData: List<ListEventsModel.Events?>?) {
      this.upcomingEventData = upcomingEventData
      notifyDataSetChanged()
   }
}