package com.example.foodivore.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.login.domain.ILogin
import com.example.foodivore.ui.main.profile.domain.IProfile
import com.example.foodivore.utils.CustomException
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val useCaseLogin: ILogin, private val useCaseProfile: IProfile) :
    ViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()


    fun loginWithEmailAndPassword(): LiveData<Resource<User.LoginResponse?>> {
        Log.d("LoginFragment", "user data : ${email.value}")
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
                    emit(Resource.Failure(CustomException("Email or Password can't be blank")))
                } else {
                    val loginAuthResult: Resource<User.LoginResponse?> =
                        useCaseLogin.loginWithEmailAndPassword(
                            email = email.value!!,
                            password = password.value!!
                        )
                    emit(loginAuthResult)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun profileChecker(jwtToken: String): LiveData<Resource<User.CalorieNeedsResponse?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val userProfile = useCaseProfile.getUserCalorie(jwtToken)
                emit(userProfile)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

}