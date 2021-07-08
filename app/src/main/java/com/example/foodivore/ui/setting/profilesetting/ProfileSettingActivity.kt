package com.example.foodivore.ui.setting.profilesetting

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
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
import com.example.foodivore.utils.getFileName
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProfileSettingActivity : AppCompatActivity(), UploadRequestBody.UploadCallback{

    val REQUEST_CODE: Int = 100

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
    private lateinit var circleImage: CircleImageView
    private lateinit var chooseImageText: MaterialTextView

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
        circleImage = findViewById(R.id.image_profile_setting)

        chooseImageText = findViewById(R.id.text_change_pic_profile_setting)
        chooseImageText.setOnClickListener {
            openGalleryForImage()
        }

    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            circleImage.setImageURI(data?.data) // handle chosen image
            data?.data?.let { uploadImageFile(it) }
        }
    }

    fun uploadImageFile(fileUri: Uri) {
        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(fileUri!!, "r", null) ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(fileUri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequestBody(file, "image", this)

        val part = MultipartBody.Part.createFormData("file", file.name, body)

        profileViewModel.uploadImage(part, sessionManager.fetchAuthToken()!!)
            .observe(this, { task ->
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

    override fun onProgressUpdate(percentage: Int) {

    }


}