package com.example.foodivore.ui.main.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.Article
import com.example.foodivore.ui.main.article.domain.IArticle
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ArticleViewModel(private val useCase: IArticle) : ViewModel() {

    lateinit var categoryResult: LiveData<Resource<List<Article.Category?>>>
    lateinit var articleResult: LiveData<Resource<List<Article.Post?>>>

    fun getArticleCategory() {
        categoryResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val articleCategoryResult = useCase.getCategory()

                emit(articleCategoryResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun getArticleByCategory(category: String) {
        articleResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val articleCategoryResult = useCase.getArticleByCategory(category)

                emit(articleCategoryResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }


}