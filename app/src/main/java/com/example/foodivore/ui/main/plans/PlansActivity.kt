package com.example.foodivore.ui.main.plans

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.ActivityPlansBinding
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.remote.plan.PlanRepoImpl
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.main.plans.adapter.AdapterFoodList
import com.example.foodivore.ui.main.plans.domain.PlanImpl
import com.example.foodivore.utils.viewobject.Resource
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialTextInputPicker
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlansActivity : AppCompatActivity() {

    private lateinit var plansDataBinding: ActivityPlansBinding

    private lateinit var plansChart: PieChart

    private lateinit var menuAutoComplete: AutoCompleteTextView

    private lateinit var recyclerFoodList: RecyclerView
    private lateinit var adapterFoodList: AdapterFoodList

    private lateinit var calendarButton: ImageView

    lateinit var sessionManager: SessionManager

    private val plansViewModel: PlansViewModel by lazy {
        ViewModelProvider(
            this,
            PlansVMFactory(PlanImpl(PlanRepoImpl()))
        ).get(PlansViewModel::class.java)
    }

    private lateinit var userData: User.PreTestData

    var totalCalorie = 0f
    var totalCarb = 0f
    var totalFat = 0f
    var totalProtein = 0f

    // Views
    private lateinit var calorieProgressBar: ProgressBar
    private lateinit var carbText: MaterialTextView
    private lateinit var fatText: MaterialTextView
    private lateinit var protText: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plans)

        sessionManager = SessionManager(this)

        plansDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_plans)

        userData = Gson().fromJson(intent.getStringExtra("USERDATA"), User.PreTestData::class.java)

        setupViews(plansDataBinding.root)

//        setupChart()

        setupMenu()

//        initViews()

    }

    private fun initViews() {
        val progressBarValue = (totalCalorie / userData.calorieNeeds) * 100
        if (progressBarValue <= 100) {
            calorieProgressBar.progress = progressBarValue.toInt()
        } else {
            calorieProgressBar.progress = 100
        }
        carbText.text = "$totalCarb gram"
        fatText.text = "$totalFat gram"
        protText.text = "$totalProtein gram"
    }

    private fun setupMenu() {
        val items = listOf("Semua", "Breakfast", "Lunch", "Dinner")
        val menuAdapter =
            ArrayAdapter(this, R.layout.item_list_food_type, R.id.text_item_list_food_type, items)
        menuAutoComplete.setAdapter(menuAdapter)

        recyclerFoodList.layoutManager = LinearLayoutManager(this)
        adapterFoodList = AdapterFoodList(this, arrayListOf())
        recyclerFoodList.adapter = adapterFoodList

        val locale = Locale("in", "ID")
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", locale)

        val currentTime = Calendar.getInstance()
        currentTime.set(Calendar.HOUR_OF_DAY, 0)

        plansViewModel.getPlanByDate(
            sessionManager.fetchAuthToken()!!,
            currentTime.timeInMillis
        ).observe(this, { task ->
            when (task) {
                is Resource.Success -> {
                    Log.d("PlanDebug", task.toString())
                    adapterFoodList.setData(task.data)
                    adapterFoodList.notifyDataSetChanged()
                    calculateCurrentCalories(task.data!!)
                }

                is Resource.Failure -> {
//
                }
                is Resource.Loading -> {

                }
            }
        })

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih tanggal")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            if (plansViewModel.getPlanByDate(sessionManager.fetchAuthToken()!!, it)
                    .hasActiveObservers()
            ) {
                plansViewModel.getPlanByDate(sessionManager.fetchAuthToken()!!, it)
                    .removeObservers(this)
            }

            plansViewModel.getPlanByDate(sessionManager.fetchAuthToken()!!, it)
                .observe(this, { task ->
                    when (task) {
                        is Resource.Success -> {
                            adapterFoodList.setData(task.data)
                            adapterFoodList.notifyDataSetChanged()

                        }

                        is Resource.Failure -> {
//
                        }

                        is Resource.Loading -> {

                        }
                    }
                })
        }

        calendarButton.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER_PLANS")
        }

    }

    private fun calculateCurrentCalories(data: List<Food.FoodResponse?>?) {
        if (data != null) {
            for (item in data) {
                item?.let {
                    totalCalorie += it.calorie
                    totalCarb += it.carb
                    totalFat += it.fat
                    totalProtein += it.prot
                }
            }
        }
        initViews()
    }

    private fun setupViews(view: View) {
//        plansChart = view.findViewById(R.id.chart_plans)
        menuAutoComplete = view.findViewById(R.id.menu_auto_complete_plans)
        recyclerFoodList = view.findViewById(R.id.recycler_food_list_plans)
        calendarButton = view.findViewById(R.id.button_calendar_plans)

        calorieProgressBar = view.findViewById(R.id.progress_bar_plans)
        fatText = view.findViewById(R.id.text_value_lemak)
        carbText = view.findViewById(R.id.text_value_karb)
        protText = view.findViewById(R.id.text_value_protein)
    }

    private fun setupChart() {
        plansChart.description.isEnabled = false
        plansChart.legend.isEnabled = false
        plansChart.dragDecelerationFrictionCoef = 0.95f

//        nutritionsChart.setCenterTextTypeface()
        plansChart.centerText = "Meals"

        plansChart.isDrawHoleEnabled = true
        plansChart.setHoleColor(resources.getColor(R.color.white))

        plansChart.setTransparentCircleColor(resources.getColor(R.color.white))
        plansChart.setTransparentCircleAlpha(110)

        plansChart.holeRadius = 48f
        plansChart.transparentCircleRadius = 52f

        plansChart.setDrawCenterText(true)

        plansChart.rotationAngle = 0f
        // enable rotation of the chart by touch
        plansChart.isRotationEnabled = false
        plansChart.isHighlightPerTapEnabled = true

        // add a selection listener
//        nutritionsChart.setOnChartValueSelectedListener(this)

        plansChart.animateY(1400, Easing.EaseInOutQuad);

        // set data
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(0f, "Makanan Pokok"))
        entries.add(PieEntry(0f, "Buah"))
        entries.add(PieEntry(0f, "Sayuran"))
        entries.add(PieEntry(0f, "Lauk Pauk"))

        val dataSet = PieDataSet(entries, "Nutritions")

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()

        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
//
//        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
//
//        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
//
//        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)

//        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)

//        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        val data = PieData(dataSet)
//        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)
//        data.setValueTypeface(tfLight)
        data.setDrawValues(true)
        plansChart.data = data

        // undo all highlights
        plansChart.highlightValues(null)

        plansChart.invalidate()

    }


}