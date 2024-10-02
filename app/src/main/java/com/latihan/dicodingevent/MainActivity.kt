package com.latihan.dicodingevent

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.latihan.dicodingevent.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)
      ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
         val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
         v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
         insets
      }
      val navView: BottomNavigationView = binding.navView
      val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
      val navController = navHostFragment.navController
      navController.addOnDestinationChangedListener { _, destination, _ ->
         when (destination.id) {
            R.id.homeFragment, R.id.searchFragment, R.id.upcomingFragment, R.id.finishedFragment,
            R.id.favouriteFragment
            -> {
               navView.visibility = View.VISIBLE
            }
            else -> navView.visibility = View.GONE
         }
      }
      navView.setupWithNavController(navController)
   }

   @Deprecated("Deprecated in Java")
   override fun onBackPressed() {
      val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
      val currentFragmentId = navHostFragment.navController.currentDestination?.id

      if (currentFragmentId == R.id.homeFragment ||
         currentFragmentId == R.id.searchFragment ||
         currentFragmentId == R.id.upcomingFragment ||
         currentFragmentId == R.id.finishedFragment) {
         finish()
      } else {
         super.onBackPressed()
      }
   }
}