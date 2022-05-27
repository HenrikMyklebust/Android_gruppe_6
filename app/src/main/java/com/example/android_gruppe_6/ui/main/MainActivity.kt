package com.example.android_gruppe_6.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android_gruppe_6.R
import com.example.android_gruppe_6.database.getDatabase
import com.example.android_gruppe_6.harborlist.HarborlistFragment
import com.example.android_gruppe_6.repository.TideRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration(setOf(
                R.id.mapsFragment,
                R.id.harbourlistFragment,
                R.id.loginFragment,
                R.id.showTideFragment,
                R.id.settingsFragment
            ))

        val myToolbar: Toolbar = findViewById(R.id.my_toolbar)
        NavigationUI.setupWithNavController(myToolbar, navController, appBarConfiguration)

        myToolbar.setTitle(R.string.app_name)
        setSupportActionBar(myToolbar)
        lifecycleScope.launch {
            TideRepository(getDatabase(application)).insertHarbors()
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bnbListView -> {
                    val nav = findNavController(R.id.nav_host_fragment)
                    nav.popBackStack()
                    nav.navigate(R.id.harbourlistFragment)
                }
                R.id.bnbMapView -> {
                    val nav = findNavController(R.id.nav_host_fragment)
                    nav.popBackStack()
                    nav.navigate(R.id.mapsFragment)
                }
                R.id.bnbLogIn -> {
                    val nav = findNavController(R.id.nav_host_fragment)
                    nav.popBackStack()
                    nav.navigate(R.id.loginFragment)
                }
                else -> false
            }
            true
        }
    }
}