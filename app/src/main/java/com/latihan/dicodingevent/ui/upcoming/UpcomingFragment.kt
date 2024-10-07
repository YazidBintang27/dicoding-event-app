package com.latihan.dicodingevent.ui.upcoming

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
import com.latihan.dicodingevent.ui.adapters.UpcomingEventAdapter
import com.latihan.dicodingevent.ui.adapters.UpcomingEventAdapter.OnItemClickCallback
import com.latihan.dicodingevent.ui.adapters.UpcomingEventAdapter.OnFavouriteClickCallback
import com.latihan.dicodingevent.databinding.FragmentUpcomingBinding
import com.latihan.dicodingevent.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

   private lateinit var binding: FragmentUpcomingBinding
   private val upcomingEventAdapter: UpcomingEventAdapter by lazy { UpcomingEventAdapter() }
   private val upcomingViewModel: UpcomingViewModel by viewModels()
   private lateinit var navController: NavController

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
      observeFavouriteEvent()
      navigateToDetail()
      navigateToSetting()
      addOrDeleteFavourite()
      if (!NetworkUtils.isNetworkAvailable(requireContext())) {
         showNoInternetWarning(view)
         return
      }
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

   @SuppressLint("ShowToast")
   private fun showNoInternetWarning(view: View) {
      Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
         .setAction(getString(R.string.retry)) {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
               Snackbar.make(view, getString(R.string.internet_connected), Snackbar.LENGTH_SHORT).dismiss()
               observeLoadingState()
               observeUpcomingEvent()
               navigateToDetail()
            } else {
               Snackbar.make(view, getString(R.string.still_no_internet), Snackbar.LENGTH_SHORT).show()
            }
         }.show()
   }

   private fun navigateToSetting() {
      binding.icSetting.setOnClickListener {
         navController.navigate(R.id.action_upcomingFragment_to_settingFragment)
      }
   }

   private fun addOrDeleteFavourite() {
      upcomingEventAdapter.setOnFavouriteClickCallback(object: OnFavouriteClickCallback {
         override fun onFavouriteClicked(
            isFavourite: Boolean,
            favouriteEventEntity: FavouriteEventEntity
         ) {
            if (isFavourite) {
               upcomingViewModel.addFavouriteEvent(favouriteEventEntity)
            } else {
               upcomingViewModel.deleteFavouriteEvent(favouriteEventEntity)
            }
         }
      })
   }

   private fun observeFavouriteEvent() {
      upcomingViewModel.favouriteEventData.observe(viewLifecycleOwner) { response ->
         Log.d("UpcomingFragment", "$response")
         upcomingEventAdapter.setFavouriteData(response)
      }
   }
}