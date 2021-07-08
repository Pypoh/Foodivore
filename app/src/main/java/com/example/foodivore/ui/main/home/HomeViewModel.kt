package com.example.foodivore.ui.main.home

import android.util.Log
import androidx.lifecycle.*
import com.example.foodivore.repository.datasource.local.data.ReminderEntity
import com.example.foodivore.repository.datasource.local.data.domain.IReminderDbHelper
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.food.domain.IFood
import com.example.foodivore.ui.main.home.domain.IHome
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeViewModel(
    private val useCase: IHome,
    private val useCaseDatabase: IReminderDbHelper,
    private val useCaseFood: IFood
) :
    ViewModel() {

    fun getPlanByDate(jwtToken: String, time: Long): LiveData<Resource<List<Food.FoodResponse?>?>> {
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

    fun getAllReminders(): LiveData<Resource<List<ReminderEntity>?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseDatabase.getAll()
                Log.d("HomeViewModelDebug", data.toString())


                emit(Resource.Success(data))
            } catch (e: Exception) {
                Log.d("MainActivityDebug", e.message!!)

                emit(Resource.Failure(e))
            }
        }
    }

    fun getFoods(): LiveData<Resource<List<Food.FoodResponse>?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseFood.getFoods()
                Log.d("HomeFragment", "data: $data")
                emit(data)
            } catch (e: Exception) {
                Log.d("HomeFragment", "error: ${e.message}")
                emit(Resource.Failure(e))
            }
        }
    }

    fun splitListBySchedule(data: List<Food.FoodResponse>): List<Pair<String, List<Food.FoodResponse>>> {
        val bySchedule = data.groupBy { it.schedule.name }
        return bySchedule.toList()
    }


}