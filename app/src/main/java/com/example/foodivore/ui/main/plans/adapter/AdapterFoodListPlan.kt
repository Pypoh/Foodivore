package com.example.foodivore.ui.main.plans.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.example.foodivore.scanner.camera.adapter.AdapterFoodCameraDialog
import com.example.foodivore.ui.main.home.adapter.FoodCatalogueAdapter
import com.google.android.material.textview.MaterialTextView

class AdapterFoodListPlan(val context: Context, var dataset: List<Record.PlanResponse?>?) :
    RecyclerView.Adapter<AdapterFoodListPlan.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(plan: Record.PlanResponse)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView = itemView.findViewById(R.id.text_item_title_plans)
        var calorie: MaterialTextView = itemView.findViewById(R.id.text_item_calorie_plans)
        var layout: ConstraintLayout = itemView.findViewById(R.id.layout_food_item)

        fun bind(model: Record.PlanResponse, listener: OnItemClickListener) {
            layout.setOnClickListener {
                listener.onItemClick(model)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_food_plans, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset?.get(position)
        if (data != null) {
            holder.title.text = "Racikan ${data.consumedAt}"

            var totalCalorie = 0f

            for (item in data.ingredient) {
                totalCalorie += item.calorie
            }

            holder.calorie.text = "$totalCalorie kkal"
        }
    }

    override fun getItemCount(): Int = dataset!!.size

    fun setData(data: List<Record.PlanResponse?>?) {
        this.dataset = data
    }
}