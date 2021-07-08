package com.example.foodivore.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.Menu
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodivore.R
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.local.data.ReminderDatabase
import com.example.foodivore.repository.datasource.local.data.ReminderEntity
import com.example.foodivore.repository.datasource.local.data.domain.ReminderDbHelperImpl
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.repository.datasource.remote.food.FoodRepoImpl
import com.example.foodivore.repository.datasource.remote.plan.PlanRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.scanner.camera.DetectorActivity
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.ui.food.domain.FoodImpl
import com.example.foodivore.ui.main.home.HomeVMFactory
import com.example.foodivore.ui.main.home.HomeViewModel
import com.example.foodivore.ui.main.home.domain.HomeImpl
import com.example.foodivore.ui.main.profile.ProfileVMFactory
import com.example.foodivore.ui.main.profile.ProfileViewModel
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

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

    private val sharedHomeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeVMFactory(
                HomeImpl(PlanRepoImpl()),
                ReminderDbHelperImpl(db),
                FoodImpl(FoodRepoImpl())
            )
        ).get(HomeViewModel::class.java)
    }

    var nextSchedule = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)
        sessionManager.fetchAuthToken()?.let { sharedProfileViewModel.getUserProfileData(it) }

        db = ReminderDatabase(this)
        checkNextMealSchedule()

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

//        askPermissionBattery()

    }

    private fun intentToCameraActivity() {
        val intent = Intent(this, DetectorActivity::class.java)
        intent.putExtra("SCHEDULE", nextSchedule)
        startActivity(intent)
    }

    private fun checkNextMealSchedule() {
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

        sharedHomeViewModel.getAllReminders().observe(this, { task ->
            when (task) {
                is Resource.Success -> {

                    for (data in task.data!!) {
                        if (currentHour < data.hour) {
                            val range = data.hour - currentHour
                            nextSchedule = data.name!!
                            break
                        }
                        val range = 24 - currentHour + task.data.first().hour
                        nextSchedule = task.data.first().name!!
                    }
                }

                is Resource.Failure -> {
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askPermissionBattery() {
        val intent = Intent()
        val packageName: String = packageName
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isIgnoringBatteryOptimizations(packageName)) {
            intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
        } else {
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packageName");
        }

        startActivity(intent)
    }
}