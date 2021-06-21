package com.example.foodivore.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodivore.R
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.local.data.ReminderDatabase
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.scanner.camera.DetectorActivity
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.ui.main.profile.ProfileVMFactory
import com.example.foodivore.ui.main.profile.ProfileViewModel
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
//    lateinit var apiClient: ApiClient

    private lateinit var navAddButton: FloatingActionButton

    lateinit var db: ReminderDatabase

    private val sharedProfileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileVMFactory(
                ProfileImpl(ProfileRepoImpl()),
                AuthImpl(AuthRepoImpl()),
            )
        ).get(ProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)
        sessionManager.fetchAuthToken()?.let { sharedProfileViewModel.getUserProfileData(it) }

        db = ReminderDatabase(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navMenu: Menu = navView.menu
        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_scan, R.id.navigation_profile))
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