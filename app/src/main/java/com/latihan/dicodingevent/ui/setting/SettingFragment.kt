package com.latihan.dicodingevent.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

   private lateinit var binding: FragmentSettingBinding
   private lateinit var navController: NavController
   private val settingViewModel: SettingViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
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
      navController = Navigation.findNavController(view)
      binding.myToolbar.setNavigationOnClickListener {
         navController.navigateUp()
      }
      setThemeSetting()
      getThemeSetting()
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
}