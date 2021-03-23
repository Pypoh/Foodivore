package com.example.foodivore.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class FoodTrendRecyclerAdapter(val context: Context, var dataset: List<Food.Catalogue>) :
    RecyclerView.Adapter<FoodTrendRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: MaterialCardView = itemView.findViewById(R.id.card_food_trend_item)
        var image: ImageView = itemView.findViewById(R.id.image_food_item)
        var titleText: MaterialTextView = itemView.findViewById(R.id.text_title_type_item)
        var exampleText: MaterialTextView = itemView.findViewById(R.id.text_title_food_item)
        var calorieText: MaterialTextView = itemView.findViewById(R.id.text_calorie_food_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_trend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.card.setCardBackgroundColor(ContextCompat.getColor(context, data.backgroundColor))
        holder.image.setImageResource(data.image)
        holder.titleText.text = data.title
        holder.exampleText.text = data.example
        holder.calorieText.text = data.calorie
    }

    override fun getItemCount(): Int = dataset.size
}