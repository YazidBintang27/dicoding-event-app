package com.latihan.dicodingevent.ui.home

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
import com.latihan.dicodingevent.adapter.FinishedEventAdapter
import com.latihan.dicodingevent.adapter.FinishedEventAdapter.OnItemClickCallback
import com.latihan.dicodingevent.adapter.UpcomingEventCarouselAdapter
import com.latihan.dicodingevent.adapter.UpcomingEventCarouselAdapter.OnUpcomingItemClickCallback
import com.latihan.dicodingevent.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

   private lateinit var binding: FragmentHomeBinding
   private val homeViewModel: HomeViewModel by viewModels()
   private val upcomingEventCarouselAdapter: UpcomingEventCarouselAdapter by lazy { UpcomingEventCarouselAdapter() }
   private val finishedEventAdapter: FinishedEventAdapter by lazy { FinishedEventAdapter() }
   private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
       binding = FragmentHomeBinding.inflate(inflater, container, false)
       return binding.root
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      navController = Navigation.findNavController(view)
      observeLoadingState()
      observeUpcomingEvent()
      observeFinishedEvent()
      navigateToDetail()
   }

   private fun observeUpcomingEvent() {
      homeViewModel.upcomingEventData.observe(viewLifecycleOwner) { response ->
         binding.rvUpcomingEvents.apply {
            adapter = upcomingEventCarouselAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            upcomingEventCarouselAdapter.setData(response)
         }
      }
   }

   private fun observeFinishedEvent() {
      homeViewModel.finishedEventData.observe(viewLifecycleOwner) { response ->
         binding.rvFinishedEvents.apply {
            adapter = finishedEventAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            finishedEventAdapter.setData(response)
         }
      }
   }

   private fun observeLoadingState() {
      homeViewModel.isLoading.observe(viewLifecycleOwner) { response ->
         showLoading(response)
      }
   }

   private fun navigateToDetail() {
      finishedEventAdapter.setOnItemClickCallback(object: OnItemClickCallback {
         override fun onItemClicked(id: Int) {
            val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            toDetailFragment.id = id
            Log.d("HomeFragment", "Id finished: $id")
            navController.navigate(toDetailFragment)
         }
      })

      upcomingEventCarouselAdapter.setOnItemClickCallback(object: OnUpcomingItemClickCallback {
         override fun onItemClicked(id: Int) {
            val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
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