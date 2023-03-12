package com.example.onlineshoptestapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.core.view.isNotEmpty
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.onlineshoptestapp.R
import com.example.onlineshoptestapp.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.fragmentContainerView2)
        binding.bottomBar.setupWithNavController(navController)
        val bottomNavBar = (binding.bottomBar[0] as BottomNavigationMenuView)
        bottomNavBar[4].setBackgroundResource(R.drawable.profile_button_selector)
    }
}