package com.example.foodivore.ui.main.plans

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.ui.main.plans.domain.IPlan
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PlansViewModel(private val useCase: IPlan) : ViewModel() {

    fun getPlanByDate(jwtToken: String, time: Long) : LiveData<Resource<List<Food.FoodResponse?>?>> {
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

}