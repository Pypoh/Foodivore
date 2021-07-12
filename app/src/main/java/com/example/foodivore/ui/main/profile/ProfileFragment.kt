package com.example.foodivore.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodivore.ui.main.MainActivity
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentProfileBinding
import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.InBoardingActivity
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.ui.main.article.ArticleActivity
import com.example.foodivore.ui.main.article.detail.ArticleDetailActivity
import com.example.foodivore.ui.main.plans.PlansActivity
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.example.foodivore.ui.setting.SettingActivity
import com.example.foodivore.ui.setting.mealschedule.MealScheduleSettingActivity
import com.example.foodivore.ui.setting.profilesetting.ProfileSettingActivity
import com.example.foodivore.utils.Constants
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var nameText: MaterialTextView
    private lateinit var heightText: MaterialTextView
    private lateinit var weightText: MaterialTextView
    private lateinit var ageText: MaterialTextView
    private lateinit var sexText: MaterialTextView
    private lateinit var activityText: MaterialTextView
    private lateinit var targetText: MaterialTextView
    private lateinit var profileImage: CircleImageView

    private lateinit var logoutButton: ImageView
    private lateinit var settingButton: ImageView

    private lateinit var editProfileButton: MaterialTextView

    private lateinit var profileDataBinding: FragmentProfileBinding

    private lateinit var layoutPanduan: RelativeLayout
    private lateinit var layoutPlans: RelativeLayout
    private lateinit var layoutArticle: RelativeLayout
    private lateinit var layoutMealSchedule: RelativeLayout

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ProfileVMFactory(
                ProfileImpl(ProfileRepoImpl()),
                AuthImpl(AuthRepoImpl()),
            )
        ).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        profileDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        setupViews(profileDataBinding.root)

        fetchData()

        return profileDataBinding.root
    }

    private fun fetchData() {
//        profileViewModel.getUserProfileData((activity as MainActivity).sessionManager.fetchAuthToken()!!)
        profileViewModel.result.observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Log.d("ProfileFragment", task.data.toString())
                    initViews(task.data)
                    profileViewModel.result.removeObservers(viewLifecycleOwner)
                }

                is Resource.Failure -> {
                    Log.d("ProfileFragment", task.throwable.message.toString())
                }

                else -> {
                }
            }
        })
    }

    private fun initViews(data: User.PreTestData?) {
        Log.d("ProfileFragment", data.toString())
        if (data != null) {
            nameText.text = data.name
            heightText.text = data.height
            weightText.text = data.weight
            ageText.text = data.age
            sexText.text = data.sex
            activityText.text = data.activity
            targetText.text = data.target

            Glide.with(requireContext())
                .load(data.imageUrl)
                .centerCrop()
                .into(profileImage)
        }
    }

    private fun setupViews(view: View) {
        nameText = view.findViewById(R.id.text_name_toolbar_profile)
        heightText = view.findViewById(R.id.text_height_value_profile)
        weightText = view.findViewById(R.id.text_weight_value_profile)
        ageText = view.findViewById(R.id.text_age_value_profile)
        sexText = view.findViewById(R.id.text_sex_value_profile)
        activityText = view.findViewById(R.id.text_activity_value_profile)
        targetText = view.findViewById(R.id.text_target_value_profile)
        profileImage = view.findViewById(R.id.image_profile_toolbar)

        logoutButton = view.findViewById(R.id.ic_logout_profile)
        logoutButton.setOnClickListener {
            (activity as MainActivity).sessionManager.deleteAuthToken()
            startActivity(Intent(requireContext(), InBoardingActivity::class.java))
            (activity as MainActivity).finish()
            ApiClient.removeInitializedApiServices()
        }

//        settingButton = view.findViewById(R.id.button_setting_profile)
//        settingButton.setOnClickListener {
//            intentToSetting()
//        }

        editProfileButton = view.findViewById(R.id.text_edit_information)
        editProfileButton.setOnClickListener {
            intentToProfileSetting()
        }



        layoutPanduan = view.findViewById(R.id.layout_panduan_profile)
        layoutPanduan.setOnClickListener {
            intentToPanduan()
        }
        layoutPlans = view.findViewById(R.id.layout_plans_profile)
        layoutPlans.setOnClickListener {
            if (profileViewModel.result.value != null) {
                profileViewModel.result.value?.let {
                    when (it) {
                        is Resource.Success -> {
                            intentToPlans(it.data!!)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
        layoutArticle = view.findViewById(R.id.layout_article_profile)
        layoutArticle.setOnClickListener {
            intentToArticle()
        }

        layoutMealSchedule = view.findViewById(R.id.layout_meal_schedule_profile)
        layoutMealSchedule.setOnClickListener {
            intentToMealScheduleSetting()
        }
    }

    private fun intentToSetting() {
        startActivity(Intent(requireContext(), SettingActivity::class.java))
    }

    private fun intentToPlans(value: User.PreTestData) {
        val intent = Intent(this.context, PlansActivity::class.java)
        intent.putExtra("USERDATA", Gson().toJson(value))
        requireContext().startActivity(intent)
    }

    private fun intentToArticle() {
        val intent = Intent(this.context, ArticleActivity::class.java)
        requireContext().startActivity(intent)
    }

    private fun intentToPanduan() {
        val intent = Intent(this.context, ArticleDetailActivity::class.java)
        intent.putExtra(Constants.PANDUAN_KEY, "Panduan Gizi Seimbang")
        requireContext().startActivity(intent)
    }

    private fun intentToProfileSetting() {
        startActivity(Intent(requireContext(), ProfileSettingActivity::class.java))
    }

    private fun intentToMealScheduleSetting() {
        startActivity(Intent(requireContext(), MealScheduleSettingActivity::class.java))
    }


}