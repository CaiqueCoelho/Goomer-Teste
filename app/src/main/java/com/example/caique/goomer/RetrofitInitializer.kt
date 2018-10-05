package com.example.caique.goomer

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-api.grupoanga.com/goomer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun restaurantsService() = retrofit.create(RestaurantsService::class.java)
}