package com.example.foodivore.ui.main.plans

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.foodivore.R
import com.example.foodivore.databinding.ActivityPlansBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

class PlansActivity : AppCompatActivity() {

    private lateinit var plansDataBinding: ActivityPlansBinding

    private lateinit var plansChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plans)

        plansDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_plans)

        setupViews(plansDataBinding.root)

        setupChart()

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
        entries.add(PieEntry(56f, "Makanan Pokok"))
        entries.add(PieEntry(13f, "Buah"))
        entries.add(PieEntry(36f, "Sayuran"))
        entries.add(PieEntry(48f, "Lauk Pauk"))

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

    private fun setupViews(view: View) {
        plansChart = view.findViewById(R.id.chart_plans)
    }
}