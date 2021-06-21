package com.example.foodivore.ui.food.catalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.datasource.local.data.domain.ReminderDbHelperImpl
import com.example.foodivore.repository.datasource.remote.food.FoodRepoImpl
import com.example.foodivore.repository.datasource.remote.plan.PlanRepoImpl
import com.example.foodivore.ui.food.catalogue.adapter.AdapterFoodCatalog
import com.example.foodivore.ui.food.domain.FoodImpl
import com.example.foodivore.ui.main.MainActivity
import com.example.foodivore.ui.main.home.HomeVMFactory
import com.example.foodivore.ui.main.home.HomeViewModel
import com.example.foodivore.ui.main.home.decoration.RecyclerViewItemDecoration
import com.example.foodivore.ui.main.home.domain.HomeImpl
import com.example.foodivore.utils.viewobject.Resource

class FoodCatalogueActivity : AppCompatActivity() {

    private lateinit var recyclerCatalogue: RecyclerView
    private lateinit var adapterFoodCatalogue: AdapterFoodCatalog

    private val foodCatalogueViewModel: FoodCatalogueViewModel by lazy {
        ViewModelProvider(
            this,
            FoodCatalogueVMFactory(FoodImpl(FoodRepoImpl()))
        ).get(FoodCatalogueViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_catalogue)

        setupViews()

        fetchData(intent.getStringExtra("SCHEDULE")!!)
    }

    private fun fetchData(schedule: String) {
        foodCatalogueViewModel.getFoodsBySchedule(schedule).observe(this, { task ->
            when (task) {
                is Resource.Success -> {
                    adapterFoodCatalogue.setData(task.data)
                    adapterFoodCatalogue.notifyDataSetChanged()
                }

                is Resource.Failure -> {
//
                }
                is Resource.Loading -> {

                }
            }
        })
    }

    private fun setupViews() {
        recyclerCatalogue = findViewById(R.id.recycler_catalogue)
        recyclerCatalogue.layoutManager = GridLayoutManager(this, 3)
        adapterFoodCatalogue = AdapterFoodCatalog(this, arrayListOf())
        recyclerCatalogue.addItemDecoration(RecyclerViewItemDecoration(16, 2))
        recyclerCatalogue.adapter = adapterFoodCatalogue
    }
}