package com.example.foodivore.ui.main.article.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.Article
import com.example.foodivore.ui.main.article.domain.IArticle
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ArticleDetailViewModel(private val useCase: IArticle) : ViewModel() {

    fun getArticleByTitle(title: String): LiveData<Resource<List<Article.Post?>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val articleResult = useCase.getArticleByTitle(title)

                emit(articleResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}