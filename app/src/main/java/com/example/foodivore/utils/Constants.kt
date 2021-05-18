package com.example.foodivore.utils

class Constants {

    companion object {

        // Timeout / Delay
        const val AUTH_TIMEOUT: Long = 5000L
        const val SPLASH_SCREEN_DELAY: Long = 3000L

        const val AUTH_KEY_NAME: String = "AUTH_KEY"
        const val SIGN_UP_KEY: Int = 1000
        const val LOG_IN_KEY: Int = 1001

        const val BASE_URL = "http://192.168.1.3:8080/"

        const val LOGIN_URL = "api/auth/login"
        const val SIGNUP_URL = "api/auth/signup"
        const val PRETEST_URL = ""

        const val FOODS_URL = "api/foods"

        const val USER_URL = "api/user"
        const val CALORIE_URL = "api/user/calorie"

        const val ARTICLE_URL = "api/articles"

        const val RECORD_URL = "api/user/record"

    }

}