package com.example.data.network

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

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

interface JebyBackendApi {
    @GET("api/gold/rates")
    suspend fun getLiveRates(): LiveRatesResponse

    @GET("api/catalog")
    suspend fun getCatalog(): List<CatalogItemResponse>

    @POST("api/orders/checkout")
    suspend fun createOrder(@Body request: OrderRequest): OrderResponse

    @GET("/health")
    suspend fun health(): Map<String, String>

    @POST("/jeby/chat")
    suspend fun sendMessage(@Body request: JebyChatRequest): JebyChatResponse

    @POST("/jeby/clear")
    suspend fun clearChat(@Body request: Map<String, String>): Map<String, Boolean>
}

data class LiveRatesResponse(
    val spotOzUsd: Double,
    val spotGramUsd: Double,
    val change24h: Double,
    val usdToBrl: Double
)

data class CatalogItemResponse(
    val id: String,
    val title: String,
    val weightGrams: Double,
    val karat: String,
    val priceBrl: Double
)

data class OrderRequest(
    val itemId: String,
    val paymentMethod: String,
    val customerPhone: String
)

data class OrderResponse(
    val orderNumber: String,
    val pixQrCode: String?,
    val pixCopyPaste: String?,
    val status: String
)
