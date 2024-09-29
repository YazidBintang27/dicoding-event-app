package com.latihan.dicodingevent.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.latihan.dicodingevent.R
import com.latihan.dicodingevent.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

   private lateinit var binding: FragmentSplashBinding
   private lateinit var navController: NavController

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      binding = FragmentSplashBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      navController = Navigation.findNavController(view)
      Handler().postDelayed({
         navController.navigate(R.id.action_splashFragment_to_homeFragment)
      }, 3000)
   }
}