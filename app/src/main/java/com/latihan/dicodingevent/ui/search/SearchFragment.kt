package com.latihan.dicodingevent.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.latihan.dicodingevent.data.local.entity.FavouriteEventEntity
import com.latihan.dicodingevent.ui.adapters.SearchEventAdapter
import com.latihan.dicodingevent.ui.adapters.SearchEventAdapter.OnItemClickCallback
import com.latihan.dicodingevent.databinding.FragmentSearchBinding
import com.latihan.dicodingevent.ui.adapters.SearchEventAdapter.OnFavouriteClickCallback
import com.latihan.dicodingevent.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

   private lateinit var binding: FragmentSearchBinding
   private lateinit var navController: NavController
   private val searchViewModel: SearchViewModel by viewModels()
   private val searchEventAdapter: SearchEventAdapter by lazy { SearchEventAdapter() }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      binding = FragmentSearchBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      navController = Navigation.findNavController(view)
      navigateToDetail()
      observeSearchEvent()
      observeFavouriteEvent()
      addOrDeleteFavourite()
      if (!NetworkUtils.isNetworkAvailable(requireContext())) {
         showNoInternetWarning(view)
         return
      }
   }

   private fun observeSearchEvent() {
      searchData()
      searchViewModel.searchEventData.observe(viewLifecycleOwner) { response ->
         binding.rvSearchResult.apply {
            adapter = searchEventAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            searchEventAdapter.setData(response)
         }
         if (response?.size == 0) {
            with (binding) {
               tvResult.visibility = View.GONE
               rvSearchResult.visibility = View.GONE
               tvNoData.visibility = View.VISIBLE
            }
         } else {
            with (binding) {
               tvResult.visibility = View.VISIBLE
               rvSearchResult.visibility = View.VISIBLE
               tvNoData.visibility = View.GONE
            }
         }
      }
   }

   private fun searchData() {
      binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
         override fun onQueryTextSubmit(query: String?): Boolean {
            return false
         }

         override fun onQueryTextChange(newText: String?): Boolean {
            searchViewModel.searchData(newText ?: "")
            observeLoadingState()
            return true
         }
      })
   }

   private fun observeLoadingState() {
      searchViewModel.isLoading.observe(viewLifecycleOwner) { response ->
         showLoading(response)
      }
   }

   private fun navigateToDetail() {
      searchEventAdapter.setOnItemClickCallback(object: OnItemClickCallback {
         override fun onItemClicked(id: Int) {
            val toDetailFragment = SearchFragmentDirections.actionSearchFragmentToDetailFragment()
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
      Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
         .setAction("Retry") {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
               Snackbar.make(view, "Internet Connected", Snackbar.LENGTH_SHORT).dismiss()
               observeLoadingState()
               navigateToDetail()
            } else {
               Snackbar.make(view, "Still no internet connection", Snackbar.LENGTH_SHORT).show()
            }
         }.show()
   }

   private fun addOrDeleteFavourite() {
      searchEventAdapter.setOnFavouriteClickCallback(object: OnFavouriteClickCallback {
         override fun onFavouriteClicked(
            isFavourite: Boolean,
            favouriteEventEntity: FavouriteEventEntity
         ) {
            if (isFavourite) {
               searchViewModel.addFavouriteEvent(favouriteEventEntity)
            } else {
               searchViewModel.deleteFavouriteEvent(favouriteEventEntity)
            }
         }
      })
   }

   private fun observeFavouriteEvent() {
      searchViewModel.favouriteEventData.observe(viewLifecycleOwner) { response ->
         Log.d("SearchFragment", "$response")
         searchEventAdapter.setFavouriteData(response)
      }
   }
}