package com.example.foodivore.ui.recommendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.ActivityRecommendationBinding
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.remote.recommendation.RecommendationRepoImpl
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.main.plans.adapter.AdapterFoodList
import com.example.foodivore.ui.recommendation.domain.RecommendationImpl
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson

class RecommendationActivity : AppCompatActivity() {

    private lateinit var recommendationDataBinding: ActivityRecommendationBinding

    private val recommendationViewModel: RecommendationViewModel by lazy {
        ViewModelProvider(
            this,
            RecommendationVMFactory(RecommendationImpl(RecommendationRepoImpl()))
        ).get(RecommendationViewModel::class.java)
    }

    lateinit var sessionManager: SessionManager

    private lateinit var userData: User.PreTestData
    var totalCalorie = 0f

    // Views
    private lateinit var calorieProgressBar: ProgressBar
    private lateinit var valueProgressBar: MaterialTextView

    private lateinit var recyclerFoodList: RecyclerView
    private lateinit var adapterFoodList: AdapterFoodList

    private lateinit var menuAutoComplete: AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        sessionManager = SessionManager(this)

        recommendationDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_recommendation)

        userData = Gson().fromJson(intent.getStringExtra("USERDATA"), User.PreTestData::class.java)

        setupViews(recommendationDataBinding.root)

        setupMenu()

    }

    private fun setupMenu() {
        val items = listOf("Sarapan", "Makan Siang", "Makan Malam", "Minuman", "Camilan")
        val menuAdapter =
            ArrayAdapter(this, R.layout.item_list_food_type, R.id.text_item_list_food_type, items)
        menuAutoComplete.setAdapter(menuAdapter)

        recyclerFoodList.layoutManager = LinearLayoutManager(this)
        adapterFoodList = AdapterFoodList(this, arrayListOf())
        recyclerFoodList.adapter = adapterFoodList

        recommendationViewModel.getRecommendation(
            sessionManager.fetchAuthToken()!!,
            "Sarapan" // ganti nanti
        ).observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    adapterFoodList.setData(task.data)
                    adapterFoodList.notifyDataSetChanged()
                }

                is Resource.Failure -> {

                }

                else -> {

                }
            }
        })

        // add menuautocomplete listener
        menuAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent!!.getItemAtPosition(position).toString()
                Log.d("RecActivity", "onItemSelected Clicked $selectedItem")
                if (recommendationViewModel.getRecommendation(
                        sessionManager.fetchAuthToken()!!,
                        selectedItem
                    ).hasObservers()
                ) {
                    recommendationViewModel.getRecommendation(
                        sessionManager.fetchAuthToken()!!,
                        selectedItem
                    ).removeObservers(this)
                }

                recommendationViewModel.getRecommendation(
                    sessionManager.fetchAuthToken()!!,
                    selectedItem
                ).observe(this, { task ->
                    when (task) {
                        is Resource.Loading -> {
                        }

                        is Resource.Success -> {
                            adapterFoodList.setData(task.data)
                            adapterFoodList.notifyDataSetChanged()
                        }

                        is Resource.Failure -> {

                        }

                        else -> {

                        }
                    }
                })
            }
    }

    private fun calculateCurrentCalories(data: List<Food.FoodResponse?>?) {
        if (data != null) {
            for (item in data) {
                item?.let {
                    totalCalorie += it.calorie
                }
            }
        }
        initProgressBar()
    }

    private fun initProgressBar() {

    }

    private fun setupViews(view: View) {
        menuAutoComplete = view.findViewById(R.id.menu_auto_complete_recommendation)
        recyclerFoodList = view.findViewById(R.id.recycler_food_list_recommendation)

        calorieProgressBar = view.findViewById(R.id.progress_bar_recommendation)
        valueProgressBar = view.findViewById(R.id.progress_value_recommendation)

    }


}