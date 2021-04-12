package com.example.foodivore.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.domain.IAuth
import com.example.foodivore.ui.auth.login.domain.ILogin
import com.example.foodivore.ui.main.profile.domain.IProfile
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.internal.userAgent
import java.lang.Exception

class ProfileViewModel(private val useCaseProfile: IProfile, private val useCaseAuth: IAuth) : ViewModel() {

    lateinit var result: LiveData<Resource<User.PreTestData?>>

    fun getUserProfileData(jwtToken: String) {
        result = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val userDataResult: Resource<User.PreTestData?> = useCaseProfile.getUserData(jwtToken)

                emit(userDataResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

}