package com.example.foodivore.ui.setting.mealschedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.notification.data.ReminderEntity
import com.example.foodivore.repository.model.Post
import com.example.foodivore.ui.pretest.adapter.ArticleAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class AdapterMealSchedule(val context: Context, var dataset: List<ReminderEntity>) :
    RecyclerView.Adapter<AdapterMealSchedule.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: ReminderEntity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_meal_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mealType: MaterialTextView = itemView.findViewById(R.id.text_meal_schedule)
        var time: MaterialTextView = itemView.findViewById(R.id.text_time_meal_schedule)
        var editButton: ImageView =
            itemView.findViewById(R.id.button_image_edit_meal_schedule)

        fun bind(model: ReminderEntity, onItemClickListener: OnItemClickListener) {
            editButton.setOnClickListener {
                onItemClickListener.onItemClick(model)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.bind(data, onItemClickListener)

        holder.mealType.text = data.type.name
        val hour = if (data.hour < 10) {
            "0${data.hour}"
        } else {
            data.hour
        }
        val minute = if (data.minute < 10) {
            "0${data.minute}"
        } else {
            data.minute
        }
        holder.time.text = "$hour:$minute"

    }

    fun getOnItemClickListener(): OnItemClickListener {
        return onItemClickListener
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setData(data: List<ReminderEntity>) {
        this.dataset = data
    }
}
