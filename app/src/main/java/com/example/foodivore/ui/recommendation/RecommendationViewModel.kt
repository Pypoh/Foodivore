package com.example.foodivore.ui.recommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.ui.food.domain.IFood
import com.example.foodivore.ui.main.plans.domain.IPlan
import com.example.foodivore.ui.recommendation.domain.IRecommendation
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RecommendationViewModel(
    private val useCase: IRecommendation,
    private val useCaseFood: IFood,
    private val useCasePlan: IPlan
) : ViewModel() {

    lateinit var result: LiveData<Resource<List<Food.FoodResponse?>?>>

    fun getRecommendation(
        jwtToken: String,
        schedule: String
    ): LiveData<Resource<List<Food.FoodResponse?>?>> {
        Log.d("RecActivityVM", "jwtToken: $jwtToken, schedule: $schedule")
        return liveData(Dispatchers.IO) {
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

    fun getIngredients(): LiveData<Resource<List<Food.IngredientResponse?>?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val result = useCaseFood.getIngredients()
                emit(result)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun splitListBySchedule(data: List<Food.IngredientResponse?>?): List<Pair<String, List<Food.IngredientResponse?>>> {
        val bySchedule = data!!.groupBy { it!!.foodtype.name }
        Log.d("RecActivityViewModel", bySchedule.toString())
        return bySchedule.toList()
    }

    fun sendPlan(
        jwtToken: String,
        planData: Record.PlanRequest
    ): LiveData<Resource<Record.PlanResponse>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val result = useCasePlan.sendPlan(jwtToken, planData)
                Log.d("RecVMPlans", "result: $result")
                emit(result)
            } catch (e: Exception) {
                Log.d("RecVMPlans", "error: ${e.message.toString()}")
                emit(Resource.Failure(e))
            }
        }
    }

    fun getSchedule(
        schedule: String
    ): LiveData<Resource<List<Food.Schedule>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val result = useCase.getSchedule(schedule)
                Log.d("CalorieMeter", "data: $result")

                emit(result)
            } catch (e: Exception) {
                Log.d("CalorieMeter", "error: ${e.message}")
                emit(Resource.Failure(e))
            }
        }
    }

}