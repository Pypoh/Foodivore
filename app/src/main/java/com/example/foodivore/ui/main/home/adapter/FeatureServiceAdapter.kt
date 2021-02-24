package com.example.foodivore.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Feature
import com.example.foodivore.repository.model.Food
import com.google.android.material.textview.MaterialTextView

class FeatureServiceAdapter(val context: Context, var dataset: List<Feature.Service>) :
    RecyclerView.Adapter<FeatureServiceAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(featureModel: Feature.Service)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: MaterialTextView = itemView.findViewById(R.id.text_feature_item)
        var layout: RelativeLayout = itemView.findViewById(R.id.layout_item_feature)

        fun bind(model: Feature.Service, listener: OnItemClickListener) {
            layout.setOnClickListener {
                listener.onItemClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_feature, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.bind(data, onItemClickListener)
        holder.text.text = data.title


    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = dataset.size
}