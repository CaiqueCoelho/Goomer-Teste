package com.example.caique.goomer

import com.example.caique.goomer.entity.ApiRestaurant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsService {

    @GET("restaurants")
    fun list(): Call<ApiGetRestaurants>

    @GET("restaurants?_page={number}")
    fun listPage(@Path("number") number: Int): Call<ApiGetRestaurants>
}