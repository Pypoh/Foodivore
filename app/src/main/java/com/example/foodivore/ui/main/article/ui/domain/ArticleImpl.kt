package com.example.foodivore.ui.main.article.ui.domain

import com.example.foodivore.repository.datasource.remote.article.IArticleRepo
import com.example.foodivore.repository.model.Article
import com.example.foodivore.utils.viewobject.Resource

class ArticleImpl(private val articleRepository: IArticleRepo) : IArticle {
    override suspend fun getCategory(): Resource<Article.Post?> = articleRepository.getCategory()

}