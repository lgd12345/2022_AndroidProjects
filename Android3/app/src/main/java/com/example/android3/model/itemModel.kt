package com.example.android3.model

data class ItemModel (
    var id: Long = 0,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null
)
