package com.example.nanopost.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.*
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.ActivityMainBinding
import androidx.core.view.WindowInsetsCompat.Type
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigation = binding.navigation
        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//        val windowInsetsController =
//            WindowCompat.getInsetsController(window,window.decorView)
//        windowInsetsController.isAppearanceLightStatusBars = true
//        windowInsetsController.isAppearanceLightNavigationBars = true
//        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentLayout) { view, windowInsets ->
//            val insets = windowInsets.getInsets(Type.systemBars())
//            view.updateLayoutParams<MarginLayoutParams>{
//                this.updateMargins(leftMargin, insets.top, rightMargin, insets.bottom)
//            }
//            WindowInsetsCompat.CONSUMED
//        }

//        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation) { view, windowInsets ->
//            val insets = windowInsets.getInsets(Type.systemBars())
//            view.updateLayoutParams{
//                this.height = insets.bottom
//            }
//            WindowInsetsCompat.CONSUMED
//        }

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.authFragment -> bottomNavigation.visibility = View.GONE
                R.id.imageFragment -> bottomNavigation.visibility = View.GONE
                R.id.createPostFragment -> bottomNavigation.visibility = View.GONE
                else -> bottomNavigation.visibility = View.VISIBLE
            }
        }

        if(navController.currentDestination?.id == R.id.authFragment) {
            bottomNavigation.visibility = View.GONE
        }

        bottomNavigation.setupWithNavController(navController)
    }
}