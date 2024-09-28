package com.latihan.dicodingevent.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

   private lateinit var binding: FragmentDetailBinding
   private lateinit var navController: NavController
   private val detailViewModel: DetailViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      binding = FragmentDetailBinding.inflate(inflater, container, false)
      return inflater.inflate(R.layout.fragment_detail, container, false)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      navController = Navigation.findNavController(view)
      binding = FragmentDetailBinding.bind(view)
      binding.myToolbar.setNavigationOnClickListener {
         navController.navigateUp()
      }
      observeLoadingState()
      val id = DetailFragmentArgs.fromBundle(arguments as Bundle).id
      observeDetailEvent(id)
   }

   private fun observeDetailEvent(id: Int) {
      detailViewModel.getEventById(id)
      detailViewModel.detailEventModel.observe(viewLifecycleOwner) { response ->
         val quota: Int = response?.quota?.minus(response.registrants ?: 0) ?: 0
         register(response?.link)
         binding.apply {
            Glide.with(ivImageCover)
               .load(response?.imageLogo)
               .error(R.drawable.noimage)
               .into(ivImageCover)
            tvTitle.text = response?.name
            tvOwner.text = getString(R.string.owner_text, response?.ownerName)
            tvQuota.text = getString(R.string.quota, "$quota")
            tvLocation.text = getString(R.string.location, response?.cityName)
            tvCategory.text = getString(R.string.category, response?.category)
            tvBeginTime.text = getString(R.string.begin_time, response?.beginTime)
            tvEndTime.text = getString(R.string.end_time, response?.endTime)
            tvDescription.text = HtmlCompat.fromHtml(
               response?.description.toString(),
               HtmlCompat.FROM_HTML_MODE_LEGACY
            )
         }
      }
   }

   private fun observeLoadingState() {
      detailViewModel.isLoading.observe(viewLifecycleOwner) { response ->
         showLoading(response)
      }
   }

   @SuppressLint("QueryPermissionsNeeded")
   private fun register(link: String?) {
      binding.btnRegister.setOnClickListener {
         val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
         startActivity(intent)
      }
   }

   private fun showLoading(isLoading: Boolean) {
      binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
   }
}