package com.example.foodivore.ui.main.article.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.auth.login.domain.ILogin
import com.example.foodivore.ui.main.article.ui.domain.IArticle
import com.example.foodivore.ui.main.profile.domain.IProfile

class ArticleVMFactory(private val useCase: IArticle) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IArticle::class.java).newInstance(useCase)
    }
}