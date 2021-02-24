package com.example.foodivore

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.foodivore.scanner.camera.DetectorActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var navAddButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navMenu: Menu = navView.menu
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_scan, R.id.navigation_profile))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val scanNavItem = navMenu.findItem(R.id.navigation_scan)
        scanNavItem.isEnabled = false

        navAddButton = findViewById(R.id.nav_button_add)
        navAddButton.setOnClickListener {
            intentToCameraActivity()
        }

    }

    private fun intentToCameraActivity() {
        val intent = Intent(this, DetectorActivity::class.java)
        startActivity(intent)
    }
}