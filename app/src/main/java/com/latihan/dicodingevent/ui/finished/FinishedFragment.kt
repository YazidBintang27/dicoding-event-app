package com.latihan.dicodingevent.ui.finished

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
import com.latihan.dicodingevent.databinding.FragmentFinishedBinding
import com.latihan.dicodingevent.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishedFragment : Fragment() {

   private lateinit var binding: FragmentFinishedBinding
   private val finishedViewModel: FinishedViewModel by viewModels()
   private val finishedEventAdapter: FinishedEventAdapter by lazy { FinishedEventAdapter() }
   private lateinit var navController: NavController

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      binding = FragmentFinishedBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      navController = Navigation.findNavController(view)
      observeLoadingState()
      observeFinishedEvent()
      observeFavouriteEvent()
      navigateToDetail()
      navigateToSetting()
      addOrDeleteFavourite()
      if (!NetworkUtils.isNetworkAvailable(requireContext())) {
         showNoInternetWarning(view)
         return
      }
   }

   private fun observeFinishedEvent() {
      finishedViewModel.finishedEventData.observe(viewLifecycleOwner) { response ->
         binding.rvFinishedEvents.apply {
            adapter = finishedEventAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            finishedEventAdapter.setData(response)
         }
      }
   }

   private fun observeLoadingState() {
      finishedViewModel.isLoading.observe(viewLifecycleOwner) { response ->
         showLoading(response)
      }
   }

   private fun navigateToDetail() {
      finishedEventAdapter.setOnItemClickCallback(object: OnItemClickCallback {
         override fun onItemClicked(id: Int) {
            val toDetailFragment = FinishedFragmentDirections
               .actionFinishedFragmentToDetailFragment()
            toDetailFragment.id = id
            Log.d("FinishedFragment", "Id: $id")
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
               navigateToDetail()
            } else {
               Snackbar.make(view, getString(R.string.still_no_internet), Snackbar.LENGTH_SHORT).show()
            }
         }.show()
   }

   private fun navigateToSetting() {
      binding.icSetting.setOnClickListener {
         navController.navigate(R.id.action_finishedFragment_to_settingFragment)
      }
   }

   private fun addOrDeleteFavourite() {
      finishedEventAdapter.setOnFavouriteClickCallback(object: FinishedEventAdapter.OnFavouriteClickCallback {
         override fun onFavouriteClicked(
            isFavourite: Boolean,
            favouriteEventEntity: FavouriteEventEntity
         ) {
            if (isFavourite) {
               finishedViewModel.addFavouriteEvent(favouriteEventEntity)
            } else {
               finishedViewModel.deleteFavouriteEvent(favouriteEventEntity)
            }
         }
      })
   }

   private fun observeFavouriteEvent() {
      finishedViewModel.favouriteEventData.observe(viewLifecycleOwner) { response ->
         Log.d("FinishedFragment", "$response")
         finishedEventAdapter.setFavouriteData(response)
      }
   }
}