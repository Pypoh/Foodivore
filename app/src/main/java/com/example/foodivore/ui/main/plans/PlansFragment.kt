package com.example.foodivore.ui.main.plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentPlansBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF


class PlansFragment : Fragment() {

    private lateinit var plansDataBinding: FragmentPlansBinding

    private lateinit var plansChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        plansDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setupViews(plansDataBinding.root)

        setupChart()

        return plansDataBinding.root
    }

    private fun setupChart() {
        plansChart.description.isEnabled = false
        plansChart.legend.isEnabled = false
        plansChart.dragDecelerationFrictionCoef = 0.95f

//        nutritionsChart.setCenterTextTypeface()
        plansChart.centerText = "Meals"

        plansChart.isDrawHoleEnabled = true
        plansChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.orange_500))
        plansChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.orange_500))

        plansChart.setTransparentCircleColor(ContextCompat.getColor(requireContext(), R.color.orange_500))
        plansChart.setTransparentCircleAlpha(110)

        plansChart.holeRadius = 8f
        plansChart.transparentCircleRadius = 10f

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
        entries.add(PieEntry(56f))
        entries.add(PieEntry(13f))
        entries.add(PieEntry(36f))

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
//        data.setValueTextSize(12f)
//        data.setValueTextColor(Color.WHITE)
//        data.setValueTypeface(tfLight)
        data.setDrawValues(false)
        plansChart.data = data

        // undo all highlights

        // undo all highlights
        plansChart.highlightValues(null)

        plansChart.invalidate()

    }

    private fun setupViews(view: View) {
        plansChart = view.findViewById(R.id.chart_plans)
    }
}