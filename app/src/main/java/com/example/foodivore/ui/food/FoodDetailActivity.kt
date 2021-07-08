package com.example.foodivore.ui.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson

class FoodDetailActivity : AppCompatActivity() {

    private var foodData: Food.FoodResponse? = null

    private lateinit var imageView: ImageView
    private lateinit var titleText: MaterialTextView

    private lateinit var nutritionsChart: PieChart

    private lateinit var textValueCarb: MaterialTextView
    private lateinit var textValueFat: MaterialTextView
    private lateinit var textValueProt: MaterialTextView

    private lateinit var toolbar: View
    private lateinit var toolbarText: MaterialTextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        foodData = Gson().fromJson(intent.getStringExtra("FOODDATA"), Food.FoodResponse::class.java)

        setupViews()

        initViews(foodData!!)

    }

    private fun initViews(foodData: Food.FoodResponse) {
        Glide.with(this)
            .load(foodData.imageUrl)
            .centerCrop()
            .into(imageView)
        titleText.text = foodData.title
        textValueCarb.text = foodData.carb.toString()
        textValueFat.text = foodData.fat.toString()
        textValueProt.text = foodData.prot.toString()

        nutritionsChart = findViewById(R.id.piechart_nutrition)

        nutritionsChart.setUsePercentValues(true)
        nutritionsChart.description.isEnabled = false
        nutritionsChart.legend.isEnabled = false
        nutritionsChart.dragDecelerationFrictionCoef = 0.95f

        nutritionsChart.centerText = "${foodData.calorie} cal"

        nutritionsChart.isDrawHoleEnabled = true
        nutritionsChart.setHoleColor(resources.getColor(R.color.white))

        nutritionsChart.setTransparentCircleColor(resources.getColor(R.color.orange_200))
        nutritionsChart.setTransparentCircleAlpha(110)

        nutritionsChart.holeRadius = 74f
        nutritionsChart.transparentCircleRadius = 76f

        nutritionsChart.setDrawCenterText(true)

        nutritionsChart.rotationAngle = 0f
        nutritionsChart.isRotationEnabled = false
        nutritionsChart.isHighlightPerTapEnabled = true

        nutritionsChart.animateY(1400, Easing.EaseInOutQuad)

        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(foodData.carb)) // Karb
        entries.add(PieEntry(foodData.fat)) // Lemak
        entries.add(PieEntry(foodData.prot)) // Protein

        val dataSet = PieDataSet(entries, "Nutritions")

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()


        colors.add(resources.getColor(R.color.orange_300))
        colors.add(resources.getColor(R.color.red_300))
        colors.add(resources.getColor(R.color.green_300))

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(false)
        nutritionsChart.data = data

        nutritionsChart.highlightValues(null)

        nutritionsChart.invalidate()
    }

    private fun setupViews() {
        imageView = findViewById(R.id.image_food_detail)
        titleText = findViewById(R.id.text_title_food_detail)
        nutritionsChart = findViewById(R.id.piechart_nutrition)
        textValueCarb = findViewById(R.id.text_value_karb)
        textValueFat = findViewById(R.id.text_value_lemak)
        textValueProt = findViewById(R.id.text_value_protein)
        toolbar = findViewById(R.id.toolbar_food_detail)
        toolbarText = toolbar.findViewById(R.id.title_toolbar)
        toolbarText.text = "Detail Makanan"
        backButton = toolbar.findViewById(R.id.back_arrow_toolbar)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}