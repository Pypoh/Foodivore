package com.example.foodivore.repository.datasource.remote.article

import com.example.foodivore.network.ApiClient
import com.example.foodivore.repository.model.Article
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class ArticleRepoImpl : IArticleRepo {

    override suspend fun getCategory(): Resource<List<Article.Category?>> {
        return try {
            val articleResult = ApiClient.getApiService().getArticleCategory()
            Resource.Success(articleResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getArticleByCategory(category: String): Resource<List<Article.Post?>> {
        return try {
            val articleResult = ApiClient.getApiService().getArticleByCategory(category)
            Resource.Success(articleResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getArticleByTitle(title: String): Resource<List<Article.Post?>> {
        return try {
            val articleResult = ApiClient.getApiService().getArticleByTitle(title)
            Resource.Success(articleResult)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


}