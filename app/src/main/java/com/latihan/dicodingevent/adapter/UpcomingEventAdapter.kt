package com.latihan.dicodingevent.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.databinding.EventCardVerticalBinding
import com.latihan.dicodingevent.models.ListEventsModel

class UpcomingEventAdapter: RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder>() {

   private var upcomingEventData: List<ListEventsModel.Events?>? = listOf()
   private lateinit var onItemClickCallback: OnItemClickCallback

   interface OnItemClickCallback {
      fun onItemClicked(id: Int)
   }

   fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
      this.onItemClickCallback = onItemClickCallback
   }

   class ViewHolder(private val binding: EventCardVerticalBinding): RecyclerView.ViewHolder(binding.root) {
      fun bind(data: ListEventsModel.Events?) {
         binding.apply {
            Glide.with(ivImage)
               .load(data?.imageLogo)
               .error(R.drawable.noimage)
               .into(ivImage)
            tvTitle.text = data?.name
            tvOwner.text = itemView.context.getString(R.string.owner_text, data?.ownerName)
            tvCategory.text = data?.category
         }
      }
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): UpcomingEventAdapter.ViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = EventCardVerticalBinding.inflate(layoutInflater, parent, false)
      return ViewHolder(binding)
   }

   override fun getItemCount(): Int {
      return upcomingEventData?.size ?: 0
   }

   override fun onBindViewHolder(holder: UpcomingEventAdapter.ViewHolder, position: Int) {
      holder.bind(upcomingEventData?.get(position))
      holder.itemView.setOnClickListener {
         onItemClickCallback.onItemClicked(upcomingEventData?.get(position)?.id ?: 0)
      }
   }

   @SuppressLint("NotifyDataSetChanged")
   fun setData(upcomingEventData: List<ListEventsModel.Events?>?) {
      this.upcomingEventData = upcomingEventData
      notifyDataSetChanged()
   }
}