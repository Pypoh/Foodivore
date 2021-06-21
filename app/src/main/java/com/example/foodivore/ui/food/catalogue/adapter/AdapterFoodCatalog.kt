package com.example.foodivore.ui.food.catalogue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.main.home.adapter.FoodCatalogueAdapter
import com.google.android.material.textview.MaterialTextView

class AdapterFoodCatalog(val context: Context, var dataset: List<Food.FoodResponse?>) :
    RecyclerView.Adapter<AdapterFoodCatalog.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image_food_item)
        var title: MaterialTextView = itemView.findViewById(R.id.title_food_item)
        var calorie: MaterialTextView = itemView.findViewById(R.id.calorie_food_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_catalogue, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.title.text = data!!.title
        holder.calorie.text = "${data.calorie} kal"
        Glide.with(context)
            .load(data.imageUrl)
            .centerCrop()
            .into(holder.image)
    }

    override fun getItemCount(): Int = dataset.size

    fun setData(data: List<Food.FoodResponse?>?) {
        this.dataset = data!!
    }
}