package com.latihan.dicodingevent.ui.home

import android.annotation.SuppressLint
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
import com.google.android.material.snackbar.Snackbar
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.ui.adapters.FinishedEventAdapter
import com.latihan.dicodingevent.ui.adapters.FinishedEventAdapter.OnItemClickCallback
import com.latihan.dicodingevent.ui.adapters.UpcomingEventCarouselAdapter
import com.latihan.dicodingevent.ui.adapters.UpcomingEventCarouselAdapter.OnFavouriteClickCallback
import com.latihan.dicodingevent.ui.adapters.UpcomingEventCarouselAdapter.OnUpcomingItemClickCallback
import com.latihan.dicodingevent.databinding.FragmentHomeBinding
import com.latihan.dicodingevent.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

   private lateinit var binding: FragmentHomeBinding
   private val homeViewModel: HomeViewModel by viewModels()
   private val upcomingEventCarouselAdapter: UpcomingEventCarouselAdapter by lazy { UpcomingEventCarouselAdapter() }
   private val finishedEventAdapter: FinishedEventAdapter by lazy { FinishedEventAdapter() }
   private lateinit var navController: NavController

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
      observeFavouriteEvent()
      observeUpcomingEvent()
      observeFinishedEvent()
      navigateToDetail()
      navigateToSetting()
      addOrDeleteFavouriteCarousel()
      addOrDeleteFavourite()
      if (!NetworkUtils.isNetworkAvailable(requireContext())) {
         showNoInternetWarning(view)
         with (binding) {
            tvUpcomingEvents.visibility = View.GONE
            tvFinishedEvents.visibility = View.GONE
         }
         return
      }
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
         }
         finishedEventAdapter.setData(response)
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
      with (binding) {
         progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
         tvUpcomingEvents.visibility = if (isLoading) View.GONE else View.VISIBLE
         tvFinishedEvents.visibility = if (isLoading) View.GONE else View.VISIBLE
      }
   }

   @SuppressLint("ShowToast")
   private fun showNoInternetWarning(view: View) {
      Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
         .setAction(getString(R.string.retry)) {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
               Snackbar.make(view, getString(R.string.internet_connected), Snackbar.LENGTH_SHORT).dismiss()
               observeLoadingState()
               observeUpcomingEvent()
               observeFinishedEvent()
               navigateToDetail()
            } else {
               Snackbar.make(view, getString(R.string.still_no_internet), Snackbar.LENGTH_SHORT).show()
            }
         }.show()
   }

   private fun navigateToSetting() {
      binding.icSetting.setOnClickListener {
         navController.navigate(R.id.action_homeFragment_to_settingFragment)
      }
   }

   private fun addOrDeleteFavouriteCarousel() {
      upcomingEventCarouselAdapter.setOnFavouriteClickCallback(object: OnFavouriteClickCallback {
         override fun onFavouriteClicked(
            isFavourite: Boolean,
            favouriteEventEntity: FavouriteEventEntity
         ) {
            if (isFavourite) {
               homeViewModel.addFavouriteEvent(favouriteEventEntity)
            } else {
               homeViewModel.deleteFavouriteEvent(favouriteEventEntity)
            }
         }
      })
   }

   private fun addOrDeleteFavourite() {
      finishedEventAdapter.setOnFavouriteClickCallback(object: FinishedEventAdapter.OnFavouriteClickCallback {
         override fun onFavouriteClicked(
            isFavourite: Boolean,
            favouriteEventEntity: FavouriteEventEntity
         ) {
            if (isFavourite) {
               homeViewModel.addFavouriteEvent(favouriteEventEntity)
            } else {
               homeViewModel.deleteFavouriteEvent(favouriteEventEntity)
            }
         }
      })
   }

   private fun observeFavouriteEvent() {
      homeViewModel.favouriteEventData.observe(viewLifecycleOwner) { response ->
         Log.d("HomeFragment", "$response")
         upcomingEventCarouselAdapter.setFavouriteData(response)
         finishedEventAdapter.setFavouriteData(response)
      }
   }
}