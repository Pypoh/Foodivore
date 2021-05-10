package com.example.foodivore.ui.main.article

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.R
import com.example.foodivore.repository.datasource.remote.article.ArticleRepoImpl
import com.example.foodivore.repository.datasource.remote.pretest.PreTestRepoImpl
import com.example.foodivore.ui.main.article.ui.ArticleViewModel
import com.example.foodivore.ui.main.article.ui.domain.ArticleImpl
import com.example.foodivore.ui.main.article.ui.main.ArticleVMFactory
import com.example.foodivore.ui.main.article.ui.main.SectionsPagerAdapter
import com.example.foodivore.ui.pretest.PreTestVMFactory
import com.example.foodivore.ui.pretest.PreTestViewModel
import com.example.foodivore.ui.pretest.domain.PreTestImpl

class ArticleActivity : AppCompatActivity() {

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }

    private fun getCategoryData() {

    }
}