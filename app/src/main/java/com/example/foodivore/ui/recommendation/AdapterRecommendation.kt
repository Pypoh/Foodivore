package com.example.foodivore.ui.recommendation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.food.FoodDetailActivity
import com.example.foodivore.ui.main.MainActivity
import com.example.foodivore.ui.main.home.adapter.FoodCatalogueAdapter
import com.example.foodivore.ui.main.home.decoration.RecyclerViewItemDecoration
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson

class AdapterRecommendation(
    val context: Context,
    var dataset: List<Pair<String, List<Food.IngredientResponse?>>>
) :
    RecyclerView.Adapter<AdapterRecommendation.ViewHolder>() {

    var totalCalorie = 0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_recommendation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.textRecycler.text = data.first
        holder.catalogueRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val adapter = AdapterRecommendationItem(context, data.second)
        holder.catalogueRecycler.addItemDecoration(RecyclerViewItemDecoration(24, 0))
        adapter.setOnItemClickListener(object : AdapterRecommendationItem.OnItemClickListener {
            override fun onItemClick(food: Food.IngredientResponse, position: Int) {
//                val intent = Intent(context, FoodDetailActivity::class.java)
//                intent.putExtra("FOODDATA", Gson().toJson(food))
//                context.startActivity(intent)
                val status = adapter.setSelection(position, food)
                if (status == "Plus") {
                    totalCalorie += food.calorie
                } else {
                    totalCalorie -= food.calorie
                }
                Log.d("RecActivity", "calorie : $totalCalorie")
                sendCalorie()

                selectOthers(food)
            }
        })
        holder.catalogueRecycler.adapter = adapter
    }

    private fun selectOthers(food: Food.IngredientResponse) {
        var temporaryCalorie = 0
        for (data in dataset) {
            if (data.first != food.foodtype.name) {
            }
        }
    }

    override fun getItemCount(): Int = dataset.size

    private fun sendCalorie() {
        if (context is RecommendationActivity) {
            context.setTotalCalorie(totalCalorie)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catalogueRecycler: RecyclerView =
            itemView.findViewById(R.id.recycler_items_recommendation)
        var textRecycler: MaterialTextView =
            itemView.findViewById(R.id.text_title_recycler_recommendation)
    }
}