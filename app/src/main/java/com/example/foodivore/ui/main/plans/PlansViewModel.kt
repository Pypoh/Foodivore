package com.example.foodivore.ui.main.plans

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.datasource.local.data.domain.IReminderDbHelper
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.main.plans.domain.IPlan
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PlansViewModel(private val useCase: IPlan, private val useCaseDatabase: IReminderDbHelper) :
    ViewModel() {

    fun getRecordByDate(
        jwtToken: String,
        time: Long
    ): LiveData<Resource<List<Record.RecordIngredient?>?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val recordResult = useCase.getRecordByDate(jwtToken, time)

                emit(recordResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun getPlanByDate(
        jwtToken: String,
        time: Long
    ): LiveData<Resource<List<Record.PlanResponse?>?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val recordResult = useCase.getPlanByDate(jwtToken, time)

                emit(recordResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun getScheduleCount(): LiveData<Resource<Int>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseDatabase.getAll()
                Log.d("HomeViewModelDebug", data.toString())


                emit(Resource.Success(data.size))
            } catch (e: Exception) {
                Log.d("MainActivityDebug", e.message!!)

                emit(Resource.Failure(e))
            }
        }
    }

    fun postRecord(
        authToken: String,
        data: Record.RecordRequest
    ): LiveData<Resource<Record.RecordResponse?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val result = useCase.postRecord(authToken, data)
                Log.d("HomeViewModelDebug", data.toString())

                emit(result)
            } catch (e: Exception) {
                Log.d("MainActivityDebug", e.message!!)

                emit(Resource.Failure(e))
            }
        }
    }

    fun deletePlan(authToken: String, planId: String): LiveData<Resource<User.DefaultResponse?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val result = useCase.deletePlan(authToken, planId)

                emit(result)
            } catch (e: Exception) {

                emit(Resource.Failure(e))
            }
        }
    }
}