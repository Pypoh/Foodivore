package com.example.foodivore.ui.splash

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.MainActivity
import com.example.foodivore.R
import com.example.foodivore.notification.data.DataUtils
import com.example.foodivore.notification.data.ReminderDatabase
import com.example.foodivore.notification.data.domain.ReminderDbHelperImpl
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.ui.auth.InBoardingActivity
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.utils.Constants
import com.example.foodivore.utils.viewobject.Resource

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var db: ReminderDatabase

    private val splashScreenViewModel: SplashScreenViewModel by lazy {
        ViewModelProvider(
            this,
            SplashScreenVMFactory(AuthImpl(AuthRepoImpl()), ReminderDbHelperImpl(db))
        ).get(SplashScreenViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)

        db = ReminderDatabase(this)

        splashScreenViewModel.checkScheduledReminderData(db, this)

        splashScreenViewModel.checkAuthInstance(this)

        splashScreenViewModel.authInstance.observe(this, {
            when (it) {
                is Resource.Success -> if (it.data != null) {
                    navigateToMainActivity()
                    Log.d("SplashScreen", "Fetch Token: ${it.data}")
                } else {
                    navigateToInBoardingActivity()
                    Log.d("SplashScreen", "Fetch Token: No Token")
                }

                is Resource.Failure -> {
//                    Thread.sleep(Constants.SPLASH_SCREEN_DELAY)
//                    navigateToMainActivity()
                    Log.d("SplashScreen", "Fetch Token: ${it.throwable.message}")
                }
                is Resource.Loading -> TODO()
            }
        })
    }

    private fun navigateToInBoardingActivity() {
        startActivity(Intent(this, InBoardingActivity::class.java))
        finish()
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}