package com.example.foodivore.ui.main.plans.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Record
import com.google.android.material.textview.MaterialTextView


class AdapterColorLegend(val context: Context, var dataset: List<Record.ColorLegend?>?) :
    RecyclerView.Adapter<AdapterColorLegend.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: MaterialTextView = itemView.findViewById(R.id.text_legend_item)
        var colorBulletin: ImageView = itemView.findViewById(R.id.color_bulletin_legend_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_color_legend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset?.get(position)
        if (data != null) {
            holder.name.text = data.name
            holder.colorBulletin.setColorFilter(ContextCompat.getColor(context, data.colorId))
        }
    }

    override fun getItemCount(): Int = dataset!!.size

    fun setData(data: List<Record.ColorLegend>) {
        this.dataset = data
    }
}