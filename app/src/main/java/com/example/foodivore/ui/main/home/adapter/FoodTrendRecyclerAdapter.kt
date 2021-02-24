package com.example.foodivore.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.google.android.material.textview.MaterialTextView

class FoodTrendRecyclerAdapter(val context: Context, var dataset: List<Food.Catalogue>) :
    RecyclerView.Adapter<FoodTrendRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image_food_item)
        var text: MaterialTextView = itemView.findViewById(R.id.text_title_type_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_trend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.text.text = data.title
    }

    override fun getItemCount(): Int = dataset.size
}