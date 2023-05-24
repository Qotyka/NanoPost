package com.example.nanopost.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.ActivityMainBinding
import com.example.nanopost.presentation.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigation = binding.navigation

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.authFragment -> bottomNavigation.visibility = View.GONE
                R.id.imageFragment -> bottomNavigation.visibility = View.GONE
                else -> bottomNavigation.visibility = View.VISIBLE
            }
        }

        if(navController.currentDestination?.id == R.id.authFragment) {
            bottomNavigation.visibility = View.GONE
        }

        bottomNavigation.setupWithNavController(navController)
    }
}