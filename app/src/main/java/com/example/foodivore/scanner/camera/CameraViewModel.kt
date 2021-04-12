package com.example.foodivore.scanner.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.food.domain.IFood
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CameraViewModel(private val useCase: IFood) : ViewModel() {

    var result: LiveData<Resource<List<Food.FoodResponse?>?>?> =
        liveData(Dispatchers.IO) { emit(Resource.Loading()) }

    fun getFoodByName(name: String) {
        Log.d("DetectionResultActivty", "Getting $name From View Model")

        result = liveData(Dispatchers.IO) {
//            emit(Resource.Loading())
            try {
                val foodResult = useCase.getFoodByName(name)
                Log.d("DetectionResultActivty", "Fetch Try")

                emit(foodResult)
            } catch (e: Exception) {
                Log.d("DetectionResultActivty", "Fetch ${e.message}")

                emit(Resource.Failure(e))
            }
        }
    }

}