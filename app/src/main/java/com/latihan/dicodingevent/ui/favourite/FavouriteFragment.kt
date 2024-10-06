package com.latihan.dicodingevent.ui.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.databinding.FragmentFavouriteBinding
import com.latihan.dicodingevent.ui.adapters.FavouriteEventAdapter
import com.latihan.dicodingevent.ui.adapters.FavouriteEventAdapter.OnItemClickCallback
import com.latihan.dicodingevent.ui.finished.FinishedFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

   private lateinit var binding: FragmentFavouriteBinding
   private lateinit var navController: NavController
   private val favouriteViewModel: FavouriteViewModel by viewModels()
   private val favouriteEventAdapter: FavouriteEventAdapter by lazy { FavouriteEventAdapter() }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      binding = FragmentFavouriteBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      navController = Navigation.findNavController(view)
      observeLoadingState()
      observeFavouriteEventData()
      navigateToSetting()
      navigateToDetail()
      deleteFavourite()
   }

   private fun observeFavouriteEventData() {
      favouriteViewModel.favouriteEventData.observe(viewLifecycleOwner) { response ->
         binding.rvFavouriteEvents.apply {
            adapter = favouriteEventAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
         }
         favouriteEventAdapter.setData(response)
      }
   }

   private fun navigateToSetting() {
      binding.icSetting.setOnClickListener {
         navController.navigate(R.id.action_favouriteFragment_to_settingFragment)
      }
   }

   private fun observeLoadingState() {
      favouriteViewModel.isLoading.observe(viewLifecycleOwner) { response ->
         showLoading(response)
      }
   }

   private fun showLoading(isLoading: Boolean) {
      with (binding) {
         progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
      }
   }

   private fun navigateToDetail() {
      favouriteEventAdapter.setOnItemClickCallback(object: OnItemClickCallback {
         override fun onItemClicked(id: Int) {
            val toDetailFragment = FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment()
            toDetailFragment.id = id
            Log.d("FavouriteFragment", "Id: $id")
            navController.navigate(toDetailFragment)
         }
      })
   }

   private fun deleteFavourite() {
      favouriteEventAdapter.setOnFavouriteClickCallback(object: FavouriteEventAdapter.OnFavouriteClickCallBack {
         override fun onFavouriteClicked(
            isFavourite: Boolean,
            favouriteEventEntity: FavouriteEventEntity
         ) {
            favouriteViewModel.deleteFavouriteEvent(favouriteEventEntity)
         }
      })
   }
}