package com.example.foodivore.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.foodivore.R
import com.example.foodivore.utils.Constants

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navHost = Navigation.findNavController(this, R.id.auth_nav_host_fragment)
        navHost.popBackStack()

        when (intent.getIntExtra(Constants.AUTH_KEY_NAME, 0)) {
            Constants.LOG_IN_KEY -> {
                navHost.navigate(R.id.navigation_login)
            }
            Constants.SIGN_UP_KEY -> {
                navHost.navigate(R.id.navigation_signup)
            }
        }
    }


}