package com.example.foodivore.ui.recommendation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.main.home.adapter.FoodCatalogueAdapter
import com.google.android.material.textview.MaterialTextView

class AdapterRecommendationItem(val context: Context, var dataset: List<Food.IngredientResponse?>) :
    RecyclerView.Adapter<AdapterRecommendationItem.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    private var selectedPosition = arrayListOf<Int>()

    interface OnItemClickListener {
        fun onItemClick(food: Food.IngredientResponse, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_catalogue, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.bind(data!!, onItemClickListener, position)
        holder.title.text = data.name
        holder.calorie.text = "${data.calorie} kal"
        Glide.with(context)
            .load(data.imageUrl)
            .centerCrop()
            .into(holder.image)
//        if (selectedPosition.contains(position)) {
//            holder.greyLayout.visibility = View.VISIBLE
//        } else {
//            holder.greyLayout.visibility = View.GONE
//        }

//        holder.greyLayout.visibility = if (selectedPosition.contains(position)) {
//            View.VISIBLE
//        } else {
//            View.GONE
//        }

        holder.greyLayout.visibility = if (data.selected) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }

    fun setSelection(position: Int, food: Food.IngredientResponse): String {
//        if (selectedPosition.contains(position)) {
//            selectedPosition.remove(position)
//            notifyDataSetChanged()
//            return "Minus"
//        }
//        selectedPosition.add(position)
//        Log.d("RecActivity", selectedPosition.toString())
//        notifyDataSetChanged()
//        return "Plus"

        if (food.selected) {
//            selectedPosition.remove(position)
            food.selected = false
            notifyDataSetChanged()
            return "Minus"
        }
//        selectedPosition.add(position)
        food.selected = true
        notifyDataSetChanged()
        return "Plus"
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = dataset.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: ConstraintLayout = itemView.findViewById(R.id.layout_item_food_catalogue)
        var image: ImageView = itemView.findViewById(R.id.image_food_item)
        var title: MaterialTextView = itemView.findViewById(R.id.title_food_item)
        var calorie: MaterialTextView = itemView.findViewById(R.id.calorie_food_item)
        var greyLayout: ConstraintLayout = itemView.findViewById(R.id.layout_select_item)

        fun bind(model: Food.IngredientResponse, listener: OnItemClickListener, position: Int) {
            layout.setOnClickListener {
                listener.onItemClick(model, position)
            }
//            greyLayout.setOnClickListener {
//                listener.onItemClick(model, position)
//            }
        }
    }
}