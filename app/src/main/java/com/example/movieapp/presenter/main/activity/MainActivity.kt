package com.example.movieapp.presenter.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.BuildCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.presenter.auth.login.LoginFragment
import com.example.movieapp.presenter.auth.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.btNv, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.btNv.isVisible =
                        destination.id == R.id.menu_home ||
                        destination.id == R.id.menu_search ||
                        destination.id == R.id.menu_favorite ||
                        destination.id == R.id.menu_download ||
                        destination.id == R.id.menu_profile
        }
    }
}