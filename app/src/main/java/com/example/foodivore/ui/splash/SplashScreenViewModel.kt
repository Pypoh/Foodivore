package com.example.foodivore.ui.splash

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.ui.auth.domain.IAuth
import com.example.foodivore.utils.Constants
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class SplashScreenViewModel(private val useCase: IAuth) : ViewModel() {

    lateinit var authInstance: LiveData<Resource<String?>>

    fun checkAuthInstance(context: Context) {
        authInstance = liveData(Dispatchers.IO) {
            try {
                val authResult: Resource<String?> = useCase.getAuthInstance(context)
                delay(Constants.SPLASH_SCREEN_DELAY)
                emit(authResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e.cause!!))
            }
        }
    }

}