package com.example.mychatbot.model.request

data class ImageRequest(
    val n: Int,
    val prompt: String,
    val size: String
)