package com.latihan.dicodingevent.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihan.dicodingevent.adapter.UpcomingEventAdapter
import com.latihan.dicodingevent.adapter.UpcomingEventAdapter.OnItemClickCallback
import com.latihan.dicodingevent.databinding.FragmentUpcomingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

   private lateinit var binding: FragmentUpcomingBinding
   private val upcomingEventAdapter: UpcomingEventAdapter by lazy { UpcomingEventAdapter() }
   private val upcomingViewModel: UpcomingViewModel by viewModels()
   private lateinit var navController: NavController

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      binding = FragmentUpcomingBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      navController = Navigation.findNavController(view)
      observeLoadingState()
      observeUpcomingEvent()
      navigateToDetail()
   }

   private fun observeUpcomingEvent() {
      upcomingViewModel.upcomingEventData.observe(viewLifecycleOwner) { response ->
         binding.rvUpcomingEvents.apply {
            adapter = upcomingEventAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            upcomingEventAdapter.setData(response)
         }
      }
   }

   private fun observeLoadingState() {
      upcomingViewModel.isLoading.observe(viewLifecycleOwner) { response ->
         showLoading(response)
      }
   }

   private fun navigateToDetail() {
      upcomingEventAdapter.setOnItemClickCallback(object: OnItemClickCallback {
         override fun onItemClicked(id: Int) {
            val toDetailFragment = UpcomingFragmentDirections.actionUpcomingFragmentToDetailFragment()
            toDetailFragment.id = id
            Log.d("HomeFragment", "Id upcoming: $id")
            navController.navigate(toDetailFragment)
         }
      })
   }

   private fun showLoading(isLoading: Boolean) {
      binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
   }
}