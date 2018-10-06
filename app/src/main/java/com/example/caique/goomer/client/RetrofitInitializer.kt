package com.example.caique.goomer.client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInitializer {

    var retrofit: Retrofit

    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        retrofit = Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl("https://dev-api.grupoanga.com/goomer/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    fun restaurantsService(): RestaurantsService = retrofit.create(RestaurantsService::class.java)
}