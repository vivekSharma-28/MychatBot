package com.example.mychatbot.model.chat

data class chatModel(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage,
    val warning: String
)