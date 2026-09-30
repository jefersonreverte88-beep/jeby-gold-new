package com.example.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.2.2:5000/"

object RetrofitClient {
    val api: JebyBackendApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JebyBackendApi::class.java)
    }
}
