package com.example.android_gruppe_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android_gruppe_6.database.getDatabase
import com.example.android_gruppe_6.repository.TideRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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

        lifecycleScope.launch {
            val response = repository.getDataNetwork("bergen")
            //println(response)
        }
    }
}