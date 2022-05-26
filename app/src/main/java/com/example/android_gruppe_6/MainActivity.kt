package com.example.android_gruppe_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android_gruppe_6.database.getDatabase
import com.example.android_gruppe_6.harborlist.HarborlistFragment
import com.example.android_gruppe_6.repository.TideRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val harborListFragment = HarborlistFragment()
    private val mapsFragment = MapsFragment()
    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
  /*      if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }*/
        val myToolbar: Toolbar = findViewById(R.id.my_toolbar)
        myToolbar.setTitle(R.string.app_name)
        setSupportActionBar(myToolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
            .build()
        NavigationUI.setupWithNavController(myToolbar, navController, appBarConfiguration)

        val repository = TideRepository(getDatabase(application))

        replaceFragment(harborListFragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bnbListView -> replaceFragment(harborListFragment)
                R.id.bnbMapView -> replaceFragment(mapsFragment)
                R.id.bnbLogIn -> replaceFragment(loginFragment)
                else -> false
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }

    }
}