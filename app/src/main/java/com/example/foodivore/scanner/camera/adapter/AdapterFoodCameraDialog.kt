package com.example.foodivore.scanner.camera.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Food
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView

class AdapterFoodCameraDialog(val context: Context, var dataset: ArrayList<Food.IngredientCount>) :
    RecyclerView.Adapter<AdapterFoodCameraDialog.ViewHolder>() {
//
//    private var lastCheckedRadioButton: MaterialRadioButton? = null
//    private var lastCheckedPosition = -1

    private var checkedListPosition = arrayListOf<Int>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView = itemView.findViewById(R.id.text_item_title_dialog)
        var calorie: MaterialTextView = itemView.findViewById(R.id.text_item_calorie_dialog)

        //        var radioButton: MaterialRadioButton = itemView.findViewById(R.id.radio_camera_dialog)
        var checkBox: MaterialCheckBox = itemView.findViewById(R.id.box_camera_dialog)
        var minusLayout: RelativeLayout = itemView.findViewById(R.id.layout_minus)
        var plusLayout: RelativeLayout = itemView.findViewById(R.id.layout_plus)
        var valueText: MaterialTextView = itemView.findViewById(R.id.value_item_camera)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_camera_dialog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.title.text = data.ingredient.name
        holder.calorie.text = data.ingredient.calorie.toString() + " kkal"
        holder.checkBox.isChecked = true
        holder.valueText.text = data.count.toString()

        holder.checkBox.setOnClickListener {
            if (checkedListPosition.contains(position)) {
                checkedListPosition.remove(position)
            } else {
                checkedListPosition.add(position)
            }
        }

        holder.minusLayout.setOnClickListener {
            data.count -= 1
            holder.valueText.text = data.count.toString()
        }

        holder.plusLayout.setOnClickListener {
            data.count += 1
            holder.valueText.text = data.count.toString()
        }

//        holder.radioButton.setOnClickListener {
//            lastCheckedRadioButton?.isChecked = false
//            lastCheckedRadioButton = holder.radioButton
//            lastCheckedPosition = position
//        }
    }

    fun getCheckedItem(): ArrayList<Int> {
        return checkedListPosition
    }

    fun getItemByPosition(position: Int): Food.IngredientCount {
        return dataset.get(position)
    }

    override fun getItemCount(): Int = dataset.size


}