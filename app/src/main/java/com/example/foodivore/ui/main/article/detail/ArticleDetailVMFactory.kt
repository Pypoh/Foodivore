package com.example.foodivore.ui.main.article.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.main.article.domain.IArticle

class ArticleDetailVMFactory(private val useCase: IArticle) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IArticle::class.java).newInstance(useCase)
    }
}