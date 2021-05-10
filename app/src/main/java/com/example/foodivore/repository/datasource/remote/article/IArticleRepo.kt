package com.example.foodivore.repository.datasource.remote.article

import com.example.foodivore.repository.model.Article
import com.example.foodivore.utils.viewobject.Resource

interface IArticleRepo {

    suspend fun getCategory() : Resource<Article.Post?>
}