package com.example.foodivore.ui.setting.profilesetting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.R
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class ProfileSettingActivity : AppCompatActivity() {

    private val profileViewModel: ProfileSettingViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileSettingVMFactory(
                ProfileImpl(ProfileRepoImpl()),
                AuthImpl(AuthRepoImpl()),
            )
        ).get(ProfileSettingViewModel::class.java)
    }

    private lateinit var sessionManager: SessionManager

    private lateinit var toolbarLayout: View
    private lateinit var toolbarText: MaterialTextView
    private lateinit var toolbarSaveButton: MaterialButton

    private lateinit var nameTextInput: TextInputEditText
    private lateinit var heightTextInput: TextInputEditText
    private lateinit var weightTextInput: TextInputEditText
    private lateinit var sexTextInput: TextInputEditText
    private lateinit var ageTextInput: TextInputEditText

    private var userData: User.PreTestData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)

        sessionManager = SessionManager(this)

        setupViews()

        fetchData()
    }

    private fun fetchData() {
        profileViewModel.getUserProfileData(sessionManager.fetchAuthToken()!!)
            .observe(this, { task ->
                when (task) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        initData(task.data)
                        Log.d("ProfileSettingActivity", task.data.toString())

                    }

                    is Resource.Failure -> {
                        Log.d("ProfileSettingActivity", task.throwable.message.toString())
                    }

                    else -> {

                    }
                }
            })
    }

    private fun initData(data: User.PreTestData?) {
        if (data != null) {
            nameTextInput.setText(data.name)
            heightTextInput.setText(data.height)
            weightTextInput.setText(data.weight)
            sexTextInput.setText(data.sex)
            ageTextInput.setText(data.age)

            userData = data
        }
    }

    private fun setupViews() {
        toolbarLayout = findViewById(R.id.toolbar_profile_setting)
        toolbarText = toolbarLayout.findViewById(R.id.title_toolbar)
        toolbarText.text = "Data Diri"
        toolbarSaveButton = toolbarLayout.findViewById(R.id.button_save_toolbar)
        toolbarSaveButton.visibility = View.VISIBLE

        toolbarSaveButton.setOnClickListener {
//            Log.d("ProfileSettingActivity", userData.toString())

            if (userData != null && sessionManager.fetchAuthToken() != null) {
                Log.d("ProfileSettingActivity", "Uploading data..")
                profileViewModel.postPreTestData(
                    User.PreTestData(
                        nameTextInput.text.toString(),
                        heightTextInput.text.toString(),
                        weightTextInput.text.toString(),
                        sexTextInput.text.toString(),
                        ageTextInput.text.toString(),
                        userData!!.activity,
                        userData!!.target,
                        userData!!.calorieNeeds
                    ), sessionManager.fetchAuthToken()!!
                ).observe(this, { task ->
                    when (task) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            Log.d("ProfileSettingActivity", task.data.toString())

                        }

                        is Resource.Failure -> {
                            Log.d("ProfileSettingActivity", task.throwable.message.toString())
                        }

                        else -> {

                        }
                    }
                })
            }

        }

        nameTextInput = findViewById(R.id.iet_name_profile_setting)
        heightTextInput = findViewById(R.id.iet_height_profile_setting)
        weightTextInput = findViewById(R.id.iet_weight_profile_setting)
        sexTextInput = findViewById(R.id.iet_sex_profile_setting)
        ageTextInput = findViewById(R.id.iet_age_profile_setting)

    }


}