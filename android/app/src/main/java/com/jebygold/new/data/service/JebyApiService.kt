package com.jebygold.new.data.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class JebyChatRequest(
    val user_id: String,
    val message: String
)

data class JebyChatResponse(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null,
    val user_id: String? = null
)

interface JebyApiService {
    @GET("/health")
    suspend fun health(): Map<String, String>

    @POST("/jeby/chat")
    suspend fun sendMessage(@Body request: JebyChatRequest): JebyChatResponse

    @POST("/jeby/clear")
    suspend fun clearChat(@Body request: Map<String, String>): Map<String, Boolean>
}
