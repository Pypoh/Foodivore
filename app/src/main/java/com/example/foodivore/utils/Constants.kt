package com.example.foodivore.utils

class Constants {

    companion object {

        // Timeout / Delay
        const val AUTH_TIMEOUT: Long = 5000L
        const val SPLASH_SCREEN_DELAY: Long = 2000L

        const val AUTH_KEY_NAME: String = "AUTH_KEY"
        const val SIGN_UP_KEY: Int = 1000
        const val LOG_IN_KEY: Int = 1001

        const val BASE_URL = "http://192.168.1.4:8080/"
        const val LOGIN_URL = "api/auth/login"
        const val SIGNUP_URL = "api/auth/signup"
        const val POSTS_URL = "posts"

    }

}