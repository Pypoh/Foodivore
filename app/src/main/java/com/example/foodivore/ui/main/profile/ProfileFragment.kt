package com.example.foodivore.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.MainActivity
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentProfileBinding
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.AuthActivity
import com.example.foodivore.ui.auth.InBoardingActivity
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.example.foodivore.utils.toast
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.textview.MaterialTextView
import kotlin.math.log

class ProfileFragment : Fragment() {

    private lateinit var nameText: MaterialTextView
    private lateinit var heightText: MaterialTextView
    private lateinit var weightText: MaterialTextView
    private lateinit var ageText: MaterialTextView
    private lateinit var sexText: MaterialTextView
    private lateinit var activityText: MaterialTextView
    private lateinit var targetText: MaterialTextView

    private lateinit var logoutButton: ImageView

    private lateinit var profileDataBinding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
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
        (activity as MainActivity).sessionManager.fetchAuthToken()?.let { profileViewModel.getUserProfileData(it) }
        profileViewModel.result.observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Log.d("ProfileFragment", task.data.toString())
                    initViews(task.data)
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
        if (data != null) {
            nameText.text = data.name
            heightText.text = data.height
            weightText.text = data.weight
            ageText.text = data.age
            sexText.text = data.sex
            activityText.text = data.activity
            targetText.text = data.target
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

        logoutButton = view.findViewById(R.id.ic_logout_profile)
        logoutButton.setOnClickListener {
            (activity as MainActivity).sessionManager.deleteAuthToken()
            startActivity(Intent(requireContext(), InBoardingActivity::class.java))
            (activity as MainActivity).finish()
        }
    }


}