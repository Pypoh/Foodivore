package com.example.foodivore.ui.main.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.R
import com.example.foodivore.repository.datasource.remote.article.ArticleRepoImpl
import com.example.foodivore.repository.model.Article
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.main.article.detail.ArticleDetailActivity
import com.example.foodivore.ui.main.article.domain.ArticleImpl
import com.example.foodivore.ui.recommendation.RecommendationActivity
import com.example.foodivore.utils.viewobject.Resource
import com.google.gson.Gson

class ArticleActivity : AppCompatActivity() {

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    private val articleViewModel: ArticleViewModel by lazy {
        ViewModelProvider(
            this,
            ArticleVMFactory(ArticleImpl(ArticleRepoImpl()))
        ).get(ArticleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        getCategoryData()

    }

    private fun getCategoryData() {
        articleViewModel.getArticleCategory()
        articleViewModel.categoryResult.observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Log.d("ArticleActivity", "data: ${task.data}")
                    val finalData = arrayListOf<Article.Category>()
                    for (item in task.data) {
                        if (item!!.name != "System") {
                            finalData.add(item)
                        }
                    }
                    sectionsPagerAdapter =
                        SectionsPagerAdapter(this, supportFragmentManager, finalData)
                    viewPager = findViewById(R.id.view_pager)
                    viewPager.adapter = sectionsPagerAdapter
                    tabs = findViewById(R.id.tabs)
                    tabs.setupWithViewPager(viewPager)
                }

                is Resource.Failure -> {
                    Log.d("ArticleActivity", "error: ${task.throwable.message}")
                }

                else -> {
                }
            }
        })
    }


}