package com.example.foodivore.ui.setting.mealschedule

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.foodivore.notification.DataUtils
import com.example.foodivore.repository.datasource.local.data.ReminderEntity
import com.example.foodivore.repository.datasource.local.data.domain.IReminderDbHelper
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealScheduleViewModel(private val useCaseDatabase: IReminderDbHelper) : ViewModel() {

    fun getScheduleData(): LiveData<Resource<List<ReminderEntity>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseDatabase.getAll()
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun updateReminder(reminder: ReminderEntity, context: Context) {
        try {
            viewModelScope.launch {
                useCaseDatabase.update(reminder)

                DataUtils.updateAlarm(context, reminder)
            }


        } catch (e: Exception) {

        }
    }

}