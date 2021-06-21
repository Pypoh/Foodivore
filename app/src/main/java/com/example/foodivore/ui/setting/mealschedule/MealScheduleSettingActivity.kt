package com.example.foodivore.ui.setting.mealschedule

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.datasource.local.data.ReminderDatabase
import com.example.foodivore.repository.datasource.local.data.ReminderEntity
import com.example.foodivore.repository.datasource.local.data.domain.ReminderDbHelperImpl
import com.example.foodivore.ui.setting.mealschedule.adapter.AdapterMealSchedule
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class MealScheduleSettingActivity : AppCompatActivity() {

    private lateinit var db: ReminderDatabase

    private val mealScheduleViewModel: MealScheduleViewModel by lazy {
        ViewModelProvider(
            this,
            MealScheduleVMFactory(ReminderDbHelperImpl(db))
        ).get(MealScheduleViewModel::class.java)
    }

    private lateinit var recyclerMealSchedule: RecyclerView
    private lateinit var adapterMealSchedule: AdapterMealSchedule

    private lateinit var toolbarLayout: View
    private lateinit var toolbarText: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_schedule_setting)

        db = ReminderDatabase(this)

        setupViews()

        initData()

    }

    private fun initData() {
        mealScheduleViewModel.getScheduleData().observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Log.d("MealSchedule", "meal data: ${task.data}")
                    adapterMealSchedule.setData(task.data)
                    adapterMealSchedule.notifyDataSetChanged()
                }

                is Resource.Failure -> {
                }

                else -> {
                }
            }
        })
    }

    private fun setupViews() {
        toolbarLayout = findViewById(R.id.toolbar_meal_schedule)
        toolbarText = toolbarLayout.findViewById(R.id.title_toolbar)
        toolbarText.text = "Jadwal Makan"

        recyclerMealSchedule = findViewById(R.id.recycler_meal_schedule)
        recyclerMealSchedule.layoutManager = LinearLayoutManager(this)
        adapterMealSchedule = AdapterMealSchedule(this, arrayListOf())
        adapterMealSchedule.setOnItemClickListener(object :
            AdapterMealSchedule.OnItemClickListener {
            override fun onItemClick(data: ReminderEntity) {
                val materialTimePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(data.hour)
                    .setMinute(data.minute)
                    .build()

                materialTimePicker.addOnPositiveButtonClickListener {
                    data.hour = materialTimePicker.hour
                    data.minute = materialTimePicker.minute
                    mealScheduleViewModel.updateReminder(data)
                    adapterMealSchedule.notifyDataSetChanged()
                }

                materialTimePicker.show(supportFragmentManager, "time_picker")
            }

        })
        recyclerMealSchedule.adapter = adapterMealSchedule


    }
}