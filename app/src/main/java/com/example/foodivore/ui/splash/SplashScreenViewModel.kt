package com.example.foodivore.ui.splash

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.foodivore.notification.data.DataUtils
import com.example.foodivore.notification.data.ReminderDatabase
import com.example.foodivore.ui.auth.domain.IAuth
import com.example.foodivore.utils.Constants
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

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

    fun checkScheduledReminderData(db: ReminderDatabase, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("SplashScreenViewModel", "getting saved schedule")
                val data = db.reminderDao().getAll()
                Log.d("SplashScreenViewModel", "saved schedule: $data")
                if (db.reminderDao().getAll().isEmpty()) {
                    for (reminder in DataUtils.getDefaultData()) {
                        db.reminderDao().insert(reminder)
                        Log.d("SplashScreenViewModel", "scheduled a reminder for $reminder")
                    }
                    DataUtils.scheduleAlarmsForData(context, DataUtils.getDefaultData())
                }
            } catch (e: Exception) {
                Log.d("SplashScreenViewModel", "error: ${e.message}")
            }
        }
    }

}