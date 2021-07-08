package com.example.foodivore.ui.main.home

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentHomeBinding
import com.example.foodivore.repository.datasource.local.data.domain.ReminderDbHelperImpl
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.repository.datasource.remote.food.FoodRepoImpl
import com.example.foodivore.repository.datasource.remote.plan.PlanRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.repository.model.Feature
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.ui.food.catalogue.FoodCatalogueActivity
import com.example.foodivore.ui.food.domain.FoodImpl
import com.example.foodivore.ui.main.MainActivity
import com.example.foodivore.ui.main.article.ArticleActivity
import com.example.foodivore.ui.main.article.detail.ArticleDetailActivity
import com.example.foodivore.ui.main.home.adapter.FeatureServiceAdapter
import com.example.foodivore.ui.main.home.adapter.FoodRecyclerAdapter
import com.example.foodivore.ui.main.home.adapter.FoodTrendRecyclerAdapter
import com.example.foodivore.ui.main.home.decoration.HorizontalSpaceItemDecoration
import com.example.foodivore.ui.main.home.decoration.RecyclerViewItemDecoration
import com.example.foodivore.ui.main.home.domain.HomeImpl
import com.example.foodivore.ui.main.plans.PlansActivity
import com.example.foodivore.ui.main.profile.ProfileVMFactory
import com.example.foodivore.ui.main.profile.ProfileViewModel
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.example.foodivore.ui.recommendation.RecommendationActivity
import com.example.foodivore.utils.Constants
import com.example.foodivore.utils.viewobject.Resource
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE: Int = 19

    private lateinit var homeDataBinding: FragmentHomeBinding

//    private val homeViewModel: HomeViewModel by lazy {
//        ViewModelProvider(
//            this,
//            HomeVMFactory(HomeImpl(HomeRepoImpl()), ProductImpl(ProductRepoImpl()))
//        ).get(HomeViewModel::class.java)
//    }

    private val sharedProfileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ProfileVMFactory(
                ProfileImpl(ProfileRepoImpl()),
                AuthImpl(AuthRepoImpl()),
            )
        ).get(ProfileViewModel::class.java)
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeVMFactory(
                HomeImpl(PlanRepoImpl()),
                ReminderDbHelperImpl((activity as MainActivity).db),
                FoodImpl(FoodRepoImpl())
            )
        ).get(HomeViewModel::class.java)
    }

    private lateinit var horizontalItemDecoration: HorizontalSpaceItemDecoration

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

    // Views
    private lateinit var usernameToolbar: MaterialTextView
    private lateinit var nextScheduleText: MaterialTextView

    private lateinit var textValueCarb: MaterialTextView
    private lateinit var textValueFat: MaterialTextView
    private lateinit var textValueProt: MaterialTextView

    private lateinit var buttonRecommendation: MaterialButton

    private lateinit var circleProfileImage: CircleImageView
    private lateinit var toolbar: View

    var totalCalorie = 0f
    var totalCarb = 0f
    var totalFat = 0f
    var totalProtein = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setupDummyData()

        setupViews(homeDataBinding.root)

        val currentTime = Calendar.getInstance()
        currentTime.set(Calendar.HOUR_OF_DAY, 0)

        homeViewModel.getPlanByDate(
            (activity as MainActivity).sessionManager.fetchAuthToken()!!,
            currentTime.timeInMillis
        ).observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Success -> {
                    calculateCurrentCalories(task.data)
                    setupChart(task.data)
                }

                is Resource.Failure -> {

                }
                is Resource.Loading -> {

                }
            }
        })

        updateLayoutData()

        checkNextMealSchedule()

        fetchFoods()

        setupChart(null)

//        requestPermission()

        return homeDataBinding.root
    }

    private fun checkNextMealSchedule() {
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

        homeViewModel.getAllReminders().observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Success -> {
                    for (data in task.data!!) {
                        if (currentHour < data.hour) {
                            val range = data.hour - currentHour
                            nextScheduleText.text = "${data.name} dalam $range jam"
                            break
                        }
                        val range = 24 - currentHour + task.data.first().hour
                        nextScheduleText.text = "${task.data.first().name} dalam $range jam"
                    }
                }

                is Resource.Failure -> {

                }
                is Resource.Loading -> {

                }
            }
        })

    }

    private fun setupDummyData() {
        if (dummyDataCatalogue.isEmpty()) {
            dummyDataCatalogue.add(
                Food.Catalogue(
                    R.color.red_500, R.drawable.salad, "Sarapan", "Salad", "200-300 Kal",
                    arrayListOf()
                )
            )

            dummyDataCatalogue.add(
                Food.Catalogue(
                    R.color.brown_500, R.drawable.meat, "Makan Siang", "Daging", "300-500 Kal",
                    arrayListOf()
                )
            )

            dummyDataCatalogue.add(
                Food.Catalogue(
                    R.color.teal_500,
                    R.drawable.milk,
                    "Makan Malam",
                    "Susu, Nasi Goreng",
                    "250-400 Kal",
                    arrayListOf()
                )
            )
        }

        if (dummyDataFeature.isEmpty()) {
            dummyDataFeature.add(
                Feature.Service("Panduan", R.drawable.ic_grid)
            )
            dummyDataFeature.add(
                Feature.Service("Plans", R.drawable.ic_calendar)
            )
            dummyDataFeature.add(
                Feature.Service("Artikel", R.drawable.ic_file_text)
            )
        }
    }

    private fun updateLayoutData() {
        sharedProfileViewModel.result.observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    if (task.data != null) {
                        usernameToolbar.text = if (task.data.name.isEmpty()) {
                            "Kamu belum login :("
                        } else {
                            task.data.name
                        }
                        Glide.with(this)
                            .load(task.data.imageUrl)
                            .placeholder(R.drawable.person)
                            .into(circleProfileImage)
                    }
                }

                is Resource.Failure -> {
                }

                else -> {
                }
            }
        })
    }

    private fun fetchFoods() {
        homeViewModel.getFoods().observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    foodRecyclerAdapter.dataset = homeViewModel.splitListBySchedule(task.data!!)
//                    Log.d("HomeFragment", foodRecyclerAdapter.dataset.toString())
//                    Log.d("HomeFragment", foodRecyclerAdapter.dataset.size.toString())
                    foodRecyclerAdapter.notifyDataSetChanged()
                }

                is Resource.Failure -> {
                }

                else -> {
                }
            }
        })
    }

    private fun setupChart(data: List<Food.FoodResponse?>?) {
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
        nutritionsChart.setHoleColor(resources.getColor(R.color.orange_200))

        nutritionsChart.setTransparentCircleColor(resources.getColor(R.color.orange_200))
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
           colors.add(ContextCompat.getColor(requireContext(), R.color.white))
       } else {
           colors.clear()
           colors.add(ContextCompat.getColor(requireContext(), R.color.orange_300))
           colors.add(ContextCompat.getColor(requireContext(), R.color.red_300))
           colors.add(ContextCompat.getColor(requireContext(), R.color.green_300))
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

        // Setup Nutrition Text
        textValueCarb.text = "$totalCarb gram"
        textValueFat.text = "$totalFat gram"
        textValueProt.text = "$totalProtein gram"
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
    }

    private fun setupViews(view: View) {
//        horizontalItemDecoration = HorizontalSpaceItemDecoration(requireContext(), R.dimen.margin_16, R.dimen.margin_16)

        recyclerFoodCatalogue = view.findViewById(R.id.recycler_food_catalogue_home)
        recyclerFoodCatalogue.layoutManager = LinearLayoutManager(
            requireContext(),
        )

        recyclerFoodCatalogue.addItemDecoration(RecyclerViewItemDecoration(24, 1))
        foodRecyclerAdapter = FoodRecyclerAdapter(requireContext(), arrayListOf())
        recyclerFoodCatalogue.adapter = foodRecyclerAdapter

        recyclerFoodTrend = view.findViewById(R.id.recycler_food_trend_home)
        recyclerFoodTrend.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        recyclerFoodTrend.addItemDecoration(RecyclerViewItemDecoration(24, 0))
        foodTrendRecyclerAdapter = FoodTrendRecyclerAdapter(requireContext(), dummyDataCatalogue)
        foodTrendRecyclerAdapter.setOnItemClickListener(object :
            FoodTrendRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(food: Food.Catalogue) {
                intentToFoodCatalogue(food.title)
            }
        })
        recyclerFoodTrend.adapter = foodTrendRecyclerAdapter

        toolbar = view.findViewById(R.id.toolbar_home)
        circleProfileImage = toolbar.findViewById(R.id.image_profile_toolbar)

//        recyclerFeature = view.findViewById(R.id.recycler_feature_home)
//        recyclerFeature.layoutManager =
//            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
//        recyclerFeature.addItemDecoration(RecyclerViewItemDecoration(24, 0))
//        featureServiceAdapter = FeatureServiceAdapter(requireContext(), dummyDataFeature)
//        featureServiceAdapter.setOnItemClickListener(object :
//            FeatureServiceAdapter.OnItemClickListener {
//            override fun onItemClick(featureModel: Feature.Service) {
//                when (featureModel.title) {
//                    "Plans" -> {
//                        if (sharedProfileViewModel.result.value != null) {
//                            sharedProfileViewModel.result.value?.let {
//                                when (it) {
//                                    is Resource.Success -> {
//                                        intentToPlans(it.data!!)
//                                    }
//                                    else -> {
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    "Panduan" -> {
//                        intentToPanduan()
//                    }
//
//                    "Artikel" -> {
//                        intentToArticle()
//                    }
//                }
//            }
//
//        })
//        recyclerFeature.adapter = featureServiceAdapter

        nutritionsChart = view.findViewById(R.id.piechart_nutrition)

        usernameToolbar = view.findViewById(R.id.text_name_toolbar)
        nextScheduleText = view.findViewById(R.id.text_schedule_status_toolbar)

        textValueCarb = view.findViewById(R.id.text_value_karb)
        textValueFat = view.findViewById(R.id.text_value_lemak)
        textValueProt = view.findViewById(R.id.text_value_protein)

        buttonRecommendation = view.findViewById(R.id.button_recommendation)
        buttonRecommendation.setOnClickListener {
            sharedProfileViewModel.result.value?.let {
                when (it) {
                    is Resource.Success -> {
                        intentToRecommendation(it.data!!)
                    }
                    else -> {
                    }
                }
            }
        }
    }


    private fun intentToRecommendation(value: User.PreTestData) {
        val intent = Intent(this.context, RecommendationActivity::class.java)
        intent.putExtra("USERDATA", Gson().toJson(value))
        requireContext().startActivity(intent)
    }

    private fun intentToFoodCatalogue(schedule: String) {
        val intent = Intent(this.context, FoodCatalogueActivity::class.java)
        intent.putExtra("SCHEDULE", schedule)
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

    fun requestPermission() {
        // Check if Android M or higher
        // Check if Android M or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + requireActivity().packageName)
            )
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(context)) {
//                    PermissionDenied();
                } else {
                    //Permission Granted-System will work
                }

            }
        }
    }
}
