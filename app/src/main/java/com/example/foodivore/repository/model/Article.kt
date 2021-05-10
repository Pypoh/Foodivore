package com.example.foodivore.repository.model

import com.google.gson.annotations.SerializedName

object Article {

    data class Post(
        @SerializedName("imageUrl")
        var imageUrl: String,

        @SerializedName("title")
        var title: String,

        @SerializedName("author")
        var author: String,

        @SerializedName("content")
        var content: String,

        @SerializedName("category")
        var category: String,
    )

    data class Category(
        @SerializedName("name")
        var name: String
    )
}