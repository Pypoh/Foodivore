package com.example.foodivore.ui.main.article.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.foodivore.repository.model.Article
import com.example.foodivore.utils.viewobject.Resource

class ArticleViewModel : ViewModel() {

    lateinit var result: LiveData<Resource<Article.Post?>>



}