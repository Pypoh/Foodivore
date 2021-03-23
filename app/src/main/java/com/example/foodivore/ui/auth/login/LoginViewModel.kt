package com.example.foodivore.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.login.domain.ILogin
import com.example.foodivore.utils.CustomException
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val useCase: ILogin) : ViewModel(){

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    lateinit var result: LiveData<Resource<User.LoginResponse?>>

    fun loginWithEmailAndPassword() {
        result = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
                    emit(Resource.Failure(CustomException("Email or Password can't be blank")))
                } else {
                    val loginAuthResult: Resource<User.LoginResponse?> = useCase.loginWithEmailAndPassword(
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

}