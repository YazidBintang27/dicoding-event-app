package com.latihan.dicodingevent.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.databinding.EventCardVerticalBinding
import com.latihan.dicodingevent.data.remote.models.ListEventsModel

class FinishedEventAdapter: RecyclerView.Adapter<FinishedEventAdapter.ViewHolder>() {

   private var finishedEventData: List<ListEventsModel.Events?>? = listOf()
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

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = EventCardVerticalBinding.inflate(layoutInflater, parent, false)
      return ViewHolder(binding)
   }

   override fun getItemCount(): Int {
      return finishedEventData?.size ?: 0
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(finishedEventData?.get(position))
      holder.itemView.setOnClickListener {
         onItemClickCallback.onItemClicked(finishedEventData?.get(position)?.id ?: 0)
      }
   }

   @SuppressLint("NotifyDataSetChanged")
   fun setData(finishedEventData: List<ListEventsModel.Events?>?) {
      this.finishedEventData = finishedEventData
      notifyDataSetChanged()
   }
}