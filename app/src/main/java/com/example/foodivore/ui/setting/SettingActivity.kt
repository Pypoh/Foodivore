package com.example.foodivore.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.example.foodivore.R
import com.example.foodivore.ui.setting.mealschedule.MealScheduleSettingActivity
import com.example.foodivore.ui.setting.profilesetting.ProfileSettingActivity
import com.google.android.material.textview.MaterialTextView

class SettingActivity : AppCompatActivity() {

    private lateinit var profileSettingLayout: RelativeLayout
    private lateinit var mealScheduleSettingLayout: RelativeLayout
    private lateinit var toolbarLayout: View
    private lateinit var toolbarText: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setupViews()
    }

    private fun setupViews() {
        toolbarLayout = findViewById(R.id.toolbar_setting)
        toolbarText = toolbarLayout.findViewById(R.id.title_toolbar)
        toolbarText.text = "Pengaturan"

        profileSettingLayout = findViewById(R.id.layout_profile_setting)
        profileSettingLayout.setOnClickListener {
            intentToProfileSetting()
        }

        mealScheduleSettingLayout = findViewById(R.id.layout_meal_schedule_setting)
        mealScheduleSettingLayout.setOnClickListener {
            intentToMealScheduleSetting()
        }
    }

    private fun intentToProfileSetting() {
        startActivity(Intent(this, ProfileSettingActivity::class.java))
    }

    private fun intentToMealScheduleSetting() {
        startActivity(Intent(this, MealScheduleSettingActivity::class.java))
    }
}