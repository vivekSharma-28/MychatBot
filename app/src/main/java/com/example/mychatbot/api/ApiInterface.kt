package com.example.mychatbot.api

import com.example.mychatbot.model.chat.chatModel
import com.example.mychatbot.model.imageresponse.GenerateImageModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("v1/completions")
    suspend fun getChat(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ): chatModel

    @POST("v1/images/generations")
    suspend fun generateImage(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ): GenerateImageModel
}