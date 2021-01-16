package com.example.foodivore.repository.model

object Food {

    data class Catalogue(
        var title: String,
        var data: ArrayList<Detail>
    )

    data class Detail(
        var imageUrl: String,
        var name: String,
        var calorie: Int
    )
}