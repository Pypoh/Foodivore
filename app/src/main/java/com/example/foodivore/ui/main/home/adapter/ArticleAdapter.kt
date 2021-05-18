package com.example.foodivore.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.repository.model.Article
import com.google.android.material.textview.MaterialTextView

class ArticleAdapter(val context: Context, var dataSet: List<Article.Post?>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(blogModel: Article.Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataBlog = dataSet[position]
        holder.bind(dataBlog!!, onItemClickListener)
        holder.authorTextBlog.text = dataBlog.author
        holder.titleTextBlog.text = dataBlog.title
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setPostData(data: List<Article.Post?>) {
        this.dataSet = data
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextBlog: MaterialTextView = itemView.findViewById(R.id.text_title_article)
        var authorTextBlog: MaterialTextView =
            itemView.findViewById(R.id.text_author_article)

        var layoutBlog: ConstraintLayout = itemView.findViewById(R.id.layout_blog)

        fun bind(model: Article.Post, listener: OnItemClickListener) {
            layoutBlog.setOnClickListener {
                listener.onItemClick(model)
            }
        }
    }

    fun getOnItemClickListener(): OnItemClickListener {
        return onItemClickListener
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}