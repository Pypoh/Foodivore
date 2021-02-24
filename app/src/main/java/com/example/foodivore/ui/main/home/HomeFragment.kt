package com.example.foodivore.ui.main.home

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentHomeBinding
import com.example.foodivore.repository.model.Feature
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.main.home.adapter.FeatureServiceAdapter
import com.example.foodivore.ui.main.home.adapter.FoodRecyclerAdapter
import com.example.foodivore.ui.main.home.adapter.FoodTrendRecyclerAdapter
import com.example.foodivore.ui.main.plans.PlansActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF


class HomeFragment : Fragment() {

    private lateinit var homeDataBinding: FragmentHomeBinding

//    private val homeViewModel: HomeViewModel by lazy {
//        ViewModelProvider(
//            this,
//            HomeVMFactory(HomeImpl(HomeRepoImpl()), ProductImpl(ProductRepoImpl()))
//        ).get(HomeViewModel::class.java)
//    }

    private lateinit var recyclerFoodCatalogue: RecyclerView
    private lateinit var foodRecyclerAdapter: FoodRecyclerAdapter

    private lateinit var recyclerFoodTrend: RecyclerView
    private lateinit var foodTrendRecyclerAdapter: FoodTrendRecyclerAdapter

    private lateinit var recyclerFeature: RecyclerView
    private lateinit var featureServiceAdapter: FeatureServiceAdapter

    private var dummyDataCatalogue = arrayListOf<Food.Catalogue>()
    private var dummyDataTrend = arrayListOf<Food.Catalogue>()
    private var dummyDataFeature = arrayListOf<Feature.Service>()

    private lateinit var nutritionsChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        dummyDataCatalogue.add(
            Food.Catalogue(
                "Title 1",
                arrayListOf(
                    Food.Detail("", "Food 1", 121, "Sarapan"),
                    Food.Detail("", "Food 2", 122, "Makan Siang"),
                    Food.Detail("", "Food 3", 123, "Makan Malang"),
                    Food.Detail("", "Food 4", 124, "Snack")
                )
            )
        )

        dummyDataCatalogue.add(
            Food.Catalogue(
                "Title 2",
                arrayListOf(
                    Food.Detail("", "Food 1", 121, "Sarapan"),
                    Food.Detail("", "Food 2", 122, "Makan Siang"),
                    Food.Detail("", "Food 3", 123, "Makan Malang"),
                    Food.Detail("", "Food 4", 124, "Snack")
                )
            )
        )

        dummyDataFeature.add(
            Feature.Service("Panduan")
        )
        dummyDataFeature.add(
            Feature.Service("Plans")
        )
        dummyDataFeature.add(
            Feature.Service("Panduan")
        )
        dummyDataFeature.add(
            Feature.Service("Panduan")
        )

        setupViews(homeDataBinding.root)

        setupChart()

        return homeDataBinding.root
    }

    private fun setupChart() {
//        nutritionsChart.setUsePercentValues(true)
        nutritionsChart.description.isEnabled = false
        nutritionsChart.legend.isEnabled = false
        nutritionsChart.dragDecelerationFrictionCoef = 0.95f

//        nutritionsChart.setCenterTextTypeface()
        nutritionsChart.centerText = "472 cal"

        nutritionsChart.isDrawHoleEnabled = true
        nutritionsChart.setHoleColor(resources.getColor(R.color.green_500))

        nutritionsChart.setTransparentCircleColor(resources.getColor(R.color.green_500))
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

        nutritionsChart.animateY(1400, Easing.EaseInOutQuad);

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
        nutritionsChart.data = data

        // undo all highlights

        // undo all highlights
        nutritionsChart.highlightValues(null)

        nutritionsChart.invalidate()
    }

    private fun setupViews(view: View) {
        recyclerFoodCatalogue = view.findViewById(R.id.recycler_food_catalogue_home)
        recyclerFoodCatalogue.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        foodRecyclerAdapter = FoodRecyclerAdapter(requireContext(), dummyDataCatalogue)
        recyclerFoodCatalogue.adapter = foodRecyclerAdapter

        recyclerFoodTrend = view.findViewById(R.id.recycler_food_trend_home)
        recyclerFoodTrend.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        foodTrendRecyclerAdapter = FoodTrendRecyclerAdapter(requireContext(), dummyDataCatalogue)
        recyclerFoodTrend.adapter = foodTrendRecyclerAdapter

        recyclerFeature = view.findViewById(R.id.recycler_feature_home)
        recyclerFeature.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        featureServiceAdapter = FeatureServiceAdapter(requireContext(), dummyDataFeature)
        featureServiceAdapter.setOnItemClickListener(object :
            FeatureServiceAdapter.OnItemClickListener {
            override fun onItemClick(featureModel: Feature.Service) {
                if (featureModel.title == "Plans") {
                    intentToPlans()
                }
            }

        })
        recyclerFeature.adapter = featureServiceAdapter

        nutritionsChart = view.findViewById(R.id.piechart_nutrition)


    }

    private fun intentToPlans() {
        val intent = Intent(this.context, PlansActivity::class.java)
        requireContext().startActivity(intent)
    }

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }
}