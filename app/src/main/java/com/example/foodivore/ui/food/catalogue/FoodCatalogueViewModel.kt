package com.example.foodivore.ui.food.catalogue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.food.domain.IFood
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FoodCatalogueViewModel(private val useCase: IFood) : ViewModel() {

    fun getFoodsBySchedule(schedule: String): LiveData<Resource<List<Food.FoodResponse?>?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCase.getFoodBySchedule(schedule)

                Log.d("FoodCatalogue", data.toString())

                emit(data)
            } catch (e: Exception) {
                Log.d("FoodCatalogue", e.message.toString())

                emit(Resource.Failure(e))
            }
        }
    }

}