package com.example.foodivore.ui.recommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.Article
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.recommendation.domain.IRecommendation
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RecommendationViewModel(private val useCase: IRecommendation) : ViewModel() {

    lateinit var result: LiveData<Resource<List<Food.FoodResponse?>?>>

    fun getRecommendation(jwtToken: String, schedule: String) : LiveData<Resource<List<Food.FoodResponse?>?>>
    {
        Log.d("RecActivityVM", "jwtToken: $jwtToken, schedule: $schedule")
        return  liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val result = useCase.getRecommendation(jwtToken, schedule)
                Log.d("RecActivityVM", "result: $result")
                emit(result)
            } catch (e: Exception) {
                Log.d("RecActivityVM", "error: ${e.message}")
                emit(Resource.Failure(e))
            }
        }
    }


}