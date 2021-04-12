package com.example.foodivore.scanner.camera.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.example.foodivore.repository.model.Post
import com.example.foodivore.ui.pretest.adapter.ArticleAdapter
import com.google.android.material.textview.MaterialTextView

class AdapterFoodCameraDialog(val context: Context, var dataset: List<Food.FoodResponse?>?) :
    RecyclerView.Adapter<AdapterFoodCameraDialog.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView = itemView.findViewById(R.id.text_item_title_dialog)
        var calorie: MaterialTextView = itemView.findViewById(R.id.text_item_calorie_dialog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_camera_dialog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset?.get(position)
        if (data != null) {
            holder.title.text = data.title
            holder.calorie.text = data.calorie.toString() + " kkal"
        }
    }

    override fun getItemCount(): Int = dataset!!.size


}