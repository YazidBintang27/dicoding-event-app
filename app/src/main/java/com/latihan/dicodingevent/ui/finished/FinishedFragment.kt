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
import com.latihan.dicodingevent.adapter.FinishedEventAdapter
import com.latihan.dicodingevent.adapter.FinishedEventAdapter.OnItemClickCallback
import com.latihan.dicodingevent.databinding.FragmentFinishedBinding
import com.latihan.dicodingevent.util.NetworkUtils
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
      navigateToDetail()
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
}