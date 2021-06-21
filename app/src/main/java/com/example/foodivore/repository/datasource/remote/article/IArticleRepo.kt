package com.example.foodivore.repository.datasource.remote.article

import com.example.foodivore.repository.model.Article
import com.example.foodivore.utils.viewobject.Resource

interface IArticleRepo {

    suspend fun getCategory() : Resource<List<Article.Category?>>
    suspend fun getArticleByCategory(category: String) : Resource<List<Article.Post?>>

    suspend fun getArticleByTitle(title: String) : Resource<List<Article.Post?>>

}