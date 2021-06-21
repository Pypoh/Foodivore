package com.example.foodivore.ui.main.article.domain

import com.example.foodivore.repository.model.Article
import com.example.foodivore.utils.viewobject.Resource

interface IArticle {

    suspend fun getCategory() : Resource<List<Article.Category?>>
    suspend fun getArticleByCategory(category: String) : Resource<List<Article.Post?>>

    suspend fun getArticleByTitle(title: String) : Resource<List<Article.Post?>>

}