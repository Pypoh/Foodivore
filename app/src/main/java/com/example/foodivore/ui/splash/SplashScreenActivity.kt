package com.example.foodivore.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foodivore.MainActivity
import com.example.foodivore.R
import com.example.foodivore.ui.auth.InBoardingActivity
import com.example.foodivore.utils.Constants

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Thread.sleep(Constants.SPLASH_SCREEN_DELAY)
        navigateToInBoardingActivity()
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