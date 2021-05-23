package com.example.foodivore.ui.setting.profilesetting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.domain.IAuth
import com.example.foodivore.ui.main.profile.domain.IProfile
import com.example.foodivore.ui.pretest.domain.IPreTest
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers

class ProfileSettingViewModel(
    private val useCaseProfile: IProfile,
    private val useCaseAuth: IAuth
) :
    ViewModel() {

    fun getUserProfileData(jwtToken: String): LiveData<Resource<User.PreTestData?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                Log.d("ProfileSettingActivity", "Fetching Profile Data..")
                val userDataResult: Resource<User.PreTestData?> =
                    useCaseProfile.getUserData(jwtToken)

                emit(userDataResult)
            } catch (e: Exception) {
                Log.d("ProfileSettingActivity", "error: ${e.message}")
                emit(Resource.Failure(e))
            }
        }
    }

    fun postPreTestData(preTestData: User.PreTestData, jwtToken: String): LiveData<Resource<User.PreTestResponse?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val postResult = useCaseProfile.postPreTestData(preTestData, jwtToken)
                emit(postResult)
            } catch (e: java.lang.Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}
