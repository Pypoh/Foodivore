package com.example.foodivore.ui.pretest.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Feature
import com.example.foodivore.repository.model.Post
import com.example.foodivore.ui.main.home.adapter.FeatureServiceAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textview.MaterialTextView

class ArticleAdapter(val context: Context, var dataset: List<Post.Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var selectedItem = -1

    interface OnItemClickListener {
        fun onItemClick(data: Post.Article)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView = itemView.findViewById(R.id.text_title_item_pre_test)
        var subtitle: MaterialTextView = itemView.findViewById(R.id.text_subtitle_item_pre_test)
        var card: MaterialCardView = itemView.findViewById(R.id.card_item_pre_test)

        fun bind(model: Post.Article, onItemClickListener: OnItemClickListener) {
            card.setOnClickListener {
                onItemClickListener.onItemClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_pre_test, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
//        holder.bind(data, onItemClickListener)
        holder.title.text = data.title
        holder.subtitle.text = data.subtitle
        val bgColor = if (selectedItem == position) {
            ContextCompat.getColor(context, R.color.orange_500)
        } else {
            ContextCompat.getColor(context, R.color.white)
        }
        val textColor = if (selectedItem == position) {
            ContextCompat.getColor(context, R.color.white)
        } else {
            ContextCompat.getColor(context, R.color.black_500)
        }
        holder.card.setBackgroundColor(bgColor)
        holder.title.setTextColor(textColor)
        holder.subtitle.setTextColor(textColor)

        holder.card.setOnClickListener {
            selectedItem = position
            notifyDataSetChanged()
        }

        Log.d("ArticleAdapter", "$selectedItem : $position")
    }

    override fun getItemCount(): Int = dataset.size

    fun getOnItemClickListener(): OnItemClickListener {
        return onItemClickListener
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun getLastCheckedItem(): Post.Article? {
        if (selectedItem == -1) {
            return null
        }
        return dataset[selectedItem]
    }

    fun getItemByPosition(position: Int): Post.Article {
        return dataset[position]
    }
}