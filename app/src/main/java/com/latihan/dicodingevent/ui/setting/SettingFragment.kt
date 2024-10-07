package com.latihan.dicodingevent.ui.setting

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.databinding.FragmentSettingBinding
import com.latihan.dicodingevent.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

   private lateinit var binding: FragmentSettingBinding
   private lateinit var navController: NavController
   private val settingViewModel: SettingViewModel by viewModels()

   private val requestPermissionLauncher =
      registerForActivityResult(
         ActivityResultContracts.RequestPermission()
      ) { isGranted: Boolean ->
         if (isGranted) {
            Toast.makeText(context, "Notifications permission granted", Toast.LENGTH_SHORT).show()
         } else {
            Toast.makeText(context, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
         }
      }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      binding = FragmentSettingBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      if (Build.VERSION.SDK_INT >= 33) {
         requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
      }
      navController = Navigation.findNavController(view)
      binding.myToolbar.setNavigationOnClickListener {
         navController.navigateUp()
      }
      setThemeSetting()
      getThemeSetting()
      setNotificationSetting()
      getNotificationSetting()
      if (!NetworkUtils.isNetworkAvailable(requireContext())) {
         showNoInternetWarning(view)
         return
      }
   }

   private fun getThemeSetting() {
      settingViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
         binding.switchTheme.isChecked = isDarkModeActive
      }
   }

   private fun setThemeSetting() {
      binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
         settingViewModel.saveThemeSetting(isChecked)
      }
   }

   private fun getNotificationSetting() {
      settingViewModel.getNotificationSetting().observe(viewLifecycleOwner) { isNotificationEnabled ->
         binding.switchReminder.isChecked = isNotificationEnabled
      }
   }

   private fun setNotificationSetting() {
      binding.switchReminder.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
         settingViewModel.saveNotificationSetting(isChecked)
      }
   }

   @SuppressLint("ShowToast")
   private fun showNoInternetWarning(view: View) {
      Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
         .setAction(getString(R.string.retry)) {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
               Snackbar.make(view, getString(R.string.internet_connected), Snackbar.LENGTH_SHORT).dismiss()
            } else {
               Snackbar.make(view, getString(R.string.still_no_internet), Snackbar.LENGTH_SHORT).show()
            }
         }.show()
   }
}