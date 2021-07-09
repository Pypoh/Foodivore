package com.example.foodivore.ui.main.plans.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.example.foodivore.scanner.camera.adapter.AdapterFoodCameraDialog
import com.google.android.material.textview.MaterialTextView

class AdapterFoodList(val context: Context, var dataset: List<Any?>?) :
    RecyclerView.Adapter<AdapterFoodList.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView = itemView.findViewById(R.id.text_item_title_plans)
        var calorie: MaterialTextView = itemView.findViewById(R.id.text_item_calorie_plans)
        var layout: ConstraintLayout = itemView.findViewById(R.id.layout_food_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_food_plans, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset?.get(position)
        if (data != null) {
            if (data is Food.FoodResponse) {
                holder.title.text = data.title
                holder.calorie.text = data.calorie.toString() + " kkal"
            } else if (data is Food.IngredientResponse) {
                
            }
        }
    }

    override fun getItemCount(): Int = dataset!!.size

    fun setData(data: List<Food.FoodResponse?>?) {
        this.dataset = data
    }
}