package com.example.foodivore.ui.pretest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Feature
import com.example.foodivore.repository.model.Post
import com.example.foodivore.ui.main.home.adapter.FeatureServiceAdapter
import com.google.android.material.textview.MaterialTextView

class ArticleAdapter(val context: Context, var dataset: List<Post.Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView = itemView.findViewById(R.id.text_title_item_pre_test)
        var subtitle: MaterialTextView = itemView.findViewById(R.id.text_subtitle_item_pre_test)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_pre_test, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.title.text = data.title
        holder.subtitle.text = data.subtitle
    }

    override fun getItemCount(): Int = dataset.size
}