package com.example.foodivore.ui.pretest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.main.profile.domain.IProfile
import com.example.foodivore.ui.pretest.domain.IPreTest
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PreTestViewModel(private val useCase: IProfile) : ViewModel() {

    fun postPreTestData(preTestData: User.PreTestData, jwtToken: String): LiveData<Resource<User.PreTestResponse?>> {
        return liveData(Dispatchers.IO) {
            Log.d("PreTestActivity", "UserData: $preTestData")
            emit(Resource.Loading())
            try {
                val postResult = useCase.postPreTestData(preTestData, jwtToken)
                emit(postResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}