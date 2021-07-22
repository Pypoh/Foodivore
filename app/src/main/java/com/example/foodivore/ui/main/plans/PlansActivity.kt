package com.example.foodivore.ui.main.plans

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.ActivityPlansBinding
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.local.data.ReminderDatabase
import com.example.foodivore.repository.datasource.local.data.domain.ReminderDbHelperImpl
import com.example.foodivore.repository.datasource.remote.plan.PlanRepoImpl
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.food.FoodDetailActivity
import com.example.foodivore.ui.main.plans.adapter.AdapterColorLegend
import com.example.foodivore.ui.main.plans.adapter.AdapterFoodListHistory
import com.example.foodivore.ui.main.plans.adapter.AdapterFoodListPlan
import com.example.foodivore.ui.main.plans.domain.PlanImpl
import com.example.foodivore.ui.recommendation.RecommendationActivity
import com.example.foodivore.utils.toast
import com.example.foodivore.utils.viewobject.Resource
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlansActivity : AppCompatActivity() {

    private lateinit var plansDataBinding: ActivityPlansBinding

    private lateinit var nutritionsChart: PieChart

    private lateinit var menuAutoComplete: AutoCompleteTextView

    private lateinit var recyclerFoodListPlan: RecyclerView
    private lateinit var adapterFoodListHistoryPlan: AdapterFoodListPlan

    private lateinit var recyclerFoodListHistory: RecyclerView
    private lateinit var adapterFoodListHistoryHistory: AdapterFoodListHistory

    private lateinit var recyclerColorLegend: RecyclerView
    private lateinit var adapterColorLegend: AdapterColorLegend
//    private var colorIds = arrayListOf(
//        R.color.green_100,
//        R.color.orange_100,
//        R.color.blue_100,
//        R.color.red_100,
//        R.color.purple_100
//    )

    var colorData = arrayListOf<Record.ColorLegend>(
        Record.ColorLegend(R.color.green_100, "Sarapan"),
        Record.ColorLegend(R.color.orange_100, "Camilan Pagi"),
        Record.ColorLegend(R.color.blue_100, "Makan Siang"),
        Record.ColorLegend(R.color.red_100, "Camilan Sore"),
        Record.ColorLegend(R.color.purple_100, "Makan Malam"),
    )

    private lateinit var calendarButton: ImageView

    lateinit var sessionManager: SessionManager

    lateinit var db: ReminderDatabase

    private val plansViewModel: PlansViewModel by lazy {
        ViewModelProvider(
            this,
            PlansVMFactory(
                PlanImpl(PlanRepoImpl()),
                ReminderDbHelperImpl(db)
            )
        ).get(PlansViewModel::class.java)
    }

    private lateinit var userData: User.PreTestData

    var totalCalorie = 0f
    var totalCarb = 0f
    var totalFat = 0f
    var totalProtein = 0f

    // Views
    private lateinit var calorieProgressBar: ProgressBar
    private lateinit var valueProgressBar: MaterialTextView
    private lateinit var carbText: MaterialTextView
    private lateinit var fatText: MaterialTextView
    private lateinit var protText: MaterialTextView
    private lateinit var dateText: MaterialTextView

    private lateinit var toolbar: View
    private lateinit var toolbarText: MaterialTextView
    private lateinit var backButton: ImageView
    private lateinit var saveButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plans)

        sessionManager = SessionManager(this)
        db = ReminderDatabase(this)

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
        valueProgressBar.text = "$totalCalorie / ${userData.calorieNeeds} kal"
        carbText.text = "$totalCarb gram"
        fatText.text = "$totalFat gram"
        protText.text = "$totalProtein gram"
    }

    private fun setupMenu() {
//        val items = listOf("Semua", "Breakfast", "Lunch", "Dinner")
//        val menuAdapter =
//            ArrayAdapter(this, R.layout.item_list_food_type, R.id.text_item_list_food_type, items)
//        menuAutoComplete.setAdapter(menuAdapter)

//        // add menuautocomplete listener
//        menuAutoComplete.onItemClickListener =
//            AdapterView.OnItemClickListener { parent, view, position, id ->
//                adapterFoodList.setData(task.data)
//                adapterFoodList.notifyDataSetChanged()
//            }


        recyclerFoodListHistory.layoutManager = LinearLayoutManager(this)
        adapterFoodListHistoryHistory = AdapterFoodListHistory(this, arrayListOf())
        adapterFoodListHistoryHistory.setOnItemClickListener(object :
            AdapterFoodListHistory.OnItemClickListener {
            override fun onItemClick(food: Record.RecordIngredient) {
                toast("AdapterFoodListHistoryClicked")
            }
        })
        recyclerFoodListHistory.adapter = adapterFoodListHistoryHistory

        recyclerFoodListPlan.layoutManager = LinearLayoutManager(this)
        adapterFoodListHistoryPlan = AdapterFoodListPlan(this, arrayListOf())
        adapterFoodListHistoryPlan.setOnItemClickListener(object :
            AdapterFoodListPlan.OnItemClickListener {
            override fun onItemClick(plan: Record.PlanResponse) {
                toast("AdapterFoodListPlanClicked")
                showSelectionDialog(plan)
            }
        })

        recyclerFoodListPlan.adapter = adapterFoodListHistoryPlan

        val locale = Locale("in", "ID")
        val sdf = SimpleDateFormat("dd/M/yyyy", locale)

        val currentDate = Calendar.getInstance().time
        dateText.text = sdf.format(currentDate)

        val currentTime = Calendar.getInstance()

        currentTime.set(Calendar.HOUR_OF_DAY, 0)

        Log.d("PlanDebug", "time: ${currentTime.timeInMillis}")


        plansViewModel.getRecordByDate(
            sessionManager.fetchAuthToken()!!,
            currentTime.timeInMillis
        ).observe(this, { task ->
            when (task) {
                is Resource.Success -> {
                    Log.d("PlanDebug", task.toString())
                    adapterFoodListHistoryHistory.setData(task.data)
                    adapterFoodListHistoryHistory.notifyDataSetChanged()
                    calculateCurrentCalories(task.data!!)
                    setupChart(task.data)
                }

                is Resource.Failure -> {
//
                }
                is Resource.Loading -> {

                }
            }
        })

        plansViewModel.getPlanByDate(
            sessionManager.fetchAuthToken()!!,
            currentTime.timeInMillis
        ).observe(this, { task ->
            when (task) {
                is Resource.Success -> {
                    adapterFoodListHistoryPlan.setData(task.data)
                    adapterFoodListHistoryPlan.notifyDataSetChanged()
                    Log.d("PlanRancangan", adapterFoodListHistoryPlan.dataset.toString())
//                    calculateCurrentCalories(task.data!!)
//                    setupChart(task.data)
                }

                is Resource.Failure -> {
                    Log.d("PlanRancangan", "data: ${task.toString()}")
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
            if (plansViewModel.getRecordByDate(sessionManager.fetchAuthToken()!!, it)
                    .hasActiveObservers()
            ) {
                plansViewModel.getRecordByDate(sessionManager.fetchAuthToken()!!, it)
                    .removeObservers(this)
            }

            if (plansViewModel.getPlanByDate(sessionManager.fetchAuthToken()!!, it)
                    .hasActiveObservers()
            ) {
                plansViewModel.getPlanByDate(sessionManager.fetchAuthToken()!!, it)
                    .removeObservers(this)
            }


            plansViewModel.getRecordByDate(sessionManager.fetchAuthToken()!!, it)
                .observe(this, { task ->
                    when (task) {
                        is Resource.Success -> {
                            adapterFoodListHistoryHistory.setData(task.data)
                            adapterFoodListHistoryHistory.notifyDataSetChanged()
                        }
                        is Resource.Failure -> {
                        }
                        is Resource.Loading -> {
                        }
                    }
                })

            plansViewModel.getPlanByDate(sessionManager.fetchAuthToken()!!, it)
                .observe(this, { task ->
                    when (task) {
                        is Resource.Success -> {
                            adapterFoodListHistoryPlan.setData(task.data)
                            adapterFoodListHistoryPlan.notifyDataSetChanged()
                        }

                        is Resource.Failure -> {
                            Log.d("PlanRancangan", "error: ${task.throwable.message}")
                        }
                        is Resource.Loading -> {

                        }
                    }
                })
            dateText.text = sdf.format(it)
        }
        calendarButton.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER_PLANS")
        }
    }

    private fun showSelectionDialog(food: Record.PlanResponse) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Jadwal Makan")
        val items = arrayOf("Edit Rancangan", "Pindahkan ke history")
        alertDialog.setItems(
            items
        ) { dialog, which ->
            when (which) {
                0 -> {
                    intentToRecommendation(food)
                }

                1 -> {
                    // Pindahkan history
                    val ingredientsData = arrayListOf<Food.IngredientRecord>()

                    for (data in food.ingredient) {
                        ingredientsData.add(Food.IngredientRecord(1, data.id))
                    }

                    val requestData = Record.RecordRequest(ingredientsData, food.consumedAt)

                    plansViewModel.deletePlan(sessionManager.fetchAuthToken()!!, food.id)
                    plansViewModel.postRecord(sessionManager.fetchAuthToken()!!, requestData)

                }
            }
        }
        val alert: AlertDialog = alertDialog.create()
        alertDialog.setOnCancelListener {
        }

        alert.setCanceledOnTouchOutside(true)
        alert.show()
    }

    private fun intentToRecommendation(food: Record.PlanResponse) {
        val intent = Intent(this, RecommendationActivity::class.java)
        intent.putExtra("RECOMMENDATION_DATA", Gson().toJson(food))
        intent.putExtra("USERDATA", Gson().toJson(userData))
        intent.putExtra("SCHEDULE", food.consumedAt)
        startActivity(intent)
    }

    private fun calculateCurrentCalories(data: List<Record.RecordIngredient?>?) {
        if (data != null) {
            for (item in data) {
                for (ingredient in item!!.ingredients) {
                    totalCalorie += (ingredient.ingredient.calorie * ingredient.count.toFloat())
                    totalCarb += (ingredient.ingredient.carb * ingredient.count.toFloat())
                    totalFat += (ingredient.ingredient.fat * ingredient.count.toFloat())
                    totalProtein += (ingredient.ingredient.prot * ingredient.count.toFloat())
                }
            }
        }
        initViews()
    }

    private fun setupViews(view: View) {
        nutritionsChart = view.findViewById(R.id.piechart_nutrition)
//        menuAutoComplete = view.findViewById(R.id.menu_auto_complete_plans)
        recyclerFoodListHistory = view.findViewById(R.id.recycler_food_list_history_plans)
        recyclerFoodListPlan = view.findViewById(R.id.recycler_food_list_plan_plans)
        calendarButton = view.findViewById(R.id.button_calendar_plans)

        calorieProgressBar = view.findViewById(R.id.progress_bar_plans)
        valueProgressBar = view.findViewById(R.id.progress_value_plans)
        fatText = view.findViewById(R.id.text_value_lemak)
        carbText = view.findViewById(R.id.text_value_karb)
        protText = view.findViewById(R.id.text_value_protein)
        dateText = view.findViewById(R.id.text_calendar_date_plans)

        recyclerColorLegend = view.findViewById(R.id.recycler_color_legend)
        val layoutManagerFlex = FlexboxLayoutManager(this)
        layoutManagerFlex.flexDirection = FlexDirection.ROW
//        layoutManagerFlex.justifyContent = JustifyContent.FLEX_END
        recyclerColorLegend.layoutManager = layoutManagerFlex

        adapterColorLegend = AdapterColorLegend(this, colorData)
        recyclerColorLegend.adapter = adapterColorLegend

        toolbar = findViewById(R.id.toolbar_plans)
        toolbarText = toolbar.findViewById(R.id.title_page)
        toolbarText.text = "Rancangan"
        backButton = toolbar.findViewById(R.id.back_arrow_white)
        backButton.setOnClickListener {
            onBackPressed()
        }

        /*// process data
        val colorList = arrayListOf<Record.ColorLegend>()
        plansViewModel.getScheduleCount().observe(this, { task ->
            when (task) {
                is Resource.Success -> {

                }
                else -> {

                }
            }
        })*/
    }

    private fun setupChart(data: List<Record.RecordIngredient?>?) {
//        nutritionsChart.setUsePercentValues(true)
        nutritionsChart.description.isEnabled = false
        nutritionsChart.legend.isEnabled = false
        nutritionsChart.dragDecelerationFrictionCoef = 0.95f

//        nutritionsChart.setCenterTextTypeface()
        nutritionsChart.centerText = "$totalCalorie cal"
//        if (totalCalorie <= 0) {
//            nutritionsChart.centerText = "0 cal"
//        } else {
//            nutritionsChart.centerText = "$totalCalorie cal"
//        }

        nutritionsChart.isDrawHoleEnabled = true
        nutritionsChart.setHoleColor(resources.getColor(R.color.white))

        nutritionsChart.setTransparentCircleColor(resources.getColor(R.color.white))
        nutritionsChart.setTransparentCircleAlpha(110)

        nutritionsChart.holeRadius = 74f
        nutritionsChart.transparentCircleRadius = 76f

        nutritionsChart.setDrawCenterText(true)

        nutritionsChart.rotationAngle = 0f
        // enable rotation of the chart by touch
        nutritionsChart.isRotationEnabled = false
        nutritionsChart.isHighlightPerTapEnabled = true

        // add a selection listener
//        nutritionsChart.setOnChartValueSelectedListener(this)

        nutritionsChart.animateY(1400, Easing.EaseInOutQuad)

        // set data
        val entries: ArrayList<PieEntry> = ArrayList()

        if (totalCalorie == 0f) {
            entries.clear()
            entries.add(PieEntry(100f))
        } else {
            // set data
            entries.clear()
            entries.add(PieEntry(totalCarb)) // Karb
            entries.add(PieEntry(totalFat)) // Lemak
            entries.add(PieEntry(totalProtein)) // Protein
        }

        val dataSet = PieDataSet(entries, "Nutritions")

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()

//        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        if (totalCalorie == 0f) {
            colors.clear()
            colors.add(ContextCompat.getColor(this, R.color.white))
        } else {
            colors.clear()
            colors.add(ContextCompat.getColor(this, R.color.orange_300))
            colors.add(ContextCompat.getColor(this, R.color.red_300))
            colors.add(ContextCompat.getColor(this, R.color.green_300))
        }
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
//        data.setValueTextSize(12f)
//        data.setValueTextColor(Color.WHITE)
//        data.setValueTypeface(tfLight)
        data.setDrawValues(false)
        nutritionsChart.data = data

        nutritionsChart.highlightValues(null)

        nutritionsChart.invalidate()
    }

    private fun intentToFoodDetail(food: Food.FoodResponse) {
        val intent = Intent(this, FoodDetailActivity::class.java)
        intent.putExtra("FOODDATA", Gson().toJson(food))
        startActivity(intent)
    }


}