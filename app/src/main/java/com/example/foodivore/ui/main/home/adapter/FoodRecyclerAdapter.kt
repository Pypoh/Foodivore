package com.example.foodivore.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.google.android.material.textview.MaterialTextView

class FoodRecyclerAdapter(val context: Context, var dataset: List<Food.Catalogue>) :
    RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.textRecycler.text = data.title
        holder.catalogueRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val adapter = FoodCatalogueAdapter(context, data.data)
        holder.catalogueRecycler.adapter = adapter


    }

    override fun getItemCount(): Int = dataset.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catalogueRecycler: RecyclerView = itemView.findViewById(R.id.recycler_items_home)
        var textRecycler: MaterialTextView = itemView.findViewById(R.id.text_title_recycler)
    }
}