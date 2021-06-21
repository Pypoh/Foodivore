package com.example.foodivore.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodivore.ui.main.MainActivity
import com.example.foodivore.R
import com.example.foodivore.utils.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class InBoardingActivity : AppCompatActivity() {

    private lateinit var buttonUserLogin: MaterialButton
    private lateinit var buttonGuestLogin: MaterialButton
    private lateinit var buttonUserSignup: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inboarding)

        setupViews()

        setupHandler()

    }

    private fun setupHandler() {
        buttonUserLogin.setOnClickListener {
            navigateToAuthActivity(Constants.LOG_IN_KEY)
        }
//        buttonGuestLogin.setOnClickListener {
//            navigateToMainActivity()
//        }
        buttonUserSignup.setOnClickListener {
            navigateToAuthActivity(Constants.SIGN_UP_KEY)
        }
    }

    private fun setupViews() {
        buttonUserLogin = findViewById(R.id.button_user_login_auth)
//        buttonGuestLogin = findViewById(R.id.button_guest_login_auth)
        buttonUserSignup = findViewById(R.id.button_signup_auth)
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToAuthActivity(key: Int) {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(Constants.AUTH_KEY_NAME, key)
        startActivity(intent)
    }
}