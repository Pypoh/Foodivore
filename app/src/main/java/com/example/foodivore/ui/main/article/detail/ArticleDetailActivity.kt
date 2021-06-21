package com.example.foodivore.ui.main.article.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodivore.R
import com.example.foodivore.databinding.ActivityArticleDetailBinding
import com.example.foodivore.repository.datasource.remote.article.ArticleRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.repository.model.Article
import com.example.foodivore.repository.model.Post
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.main.article.SectionsPagerAdapter
import com.example.foodivore.ui.main.article.domain.ArticleImpl
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.example.foodivore.ui.pretest.PreTestVMFactory
import com.example.foodivore.ui.pretest.PreTestViewModel
import com.example.foodivore.utils.Constants
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var articleDetailBinding: ActivityArticleDetailBinding

    private val articleDetailViewModel: ArticleDetailViewModel by lazy {
        ViewModelProvider(
            this,
            ArticleDetailVMFactory(ArticleImpl(ArticleRepoImpl()))
        ).get(ArticleDetailViewModel::class.java)
    }

    // Views
    private lateinit var titleText: MaterialTextView
    private lateinit var contentText: MaterialTextView
    private lateinit var imageView: ImageView

    private lateinit var articleData: Article.Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        articleDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_article_detail)

        setupViews(articleDetailBinding.root)

        val titleRequest = intent.getStringExtra(Constants.PANDUAN_KEY)

        if (!titleRequest.isNullOrEmpty()) {
            fetchArticleDataByTitle(titleRequest)
        }

        articleData = Gson().fromJson(intent.getStringExtra("ARTICLEDATA"), Article.Post::class.java)

        initViews(arrayListOf(articleData))
    }

    private fun setupViews(view: View) {
        titleText = view.findViewById(R.id.title_article_detail)
        contentText = view.findViewById(R.id.content_article_detail)
        imageView = view.findViewById(R.id.image_article_detail)
    }

    private fun fetchArticleDataByTitle(title: String) {
        articleDetailViewModel.getArticleByTitle(title).observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    initViews(task.data)
                }

                is Resource.Failure -> {
                    Log.d("ArticleDetailActivity", "error: ${task.throwable.message}")
                }

                else -> {
                }
            }
        })
    }

    private fun initViews(data: List<Article.Post?>) {
        val article = data.first()
        if (article != null) {
            titleText.text = article.title
            val formattedContent = article.content.replace("_b", "\n")
            contentText.text = formattedContent

            Glide.with(this)
                .load(article.imageUrl)
                .into(imageView)
        }
    }
}