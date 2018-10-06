package com.example.caique.goomer.client

import com.example.caique.goomer.entity.ApiGetRestaurants
import com.example.caique.goomer.entity.ApiItemMenu
import com.example.caique.goomer.entity.ApiReviews
import retrofit2.Call
import retrofit2.http.*

interface RestaurantsService {

    @GET("restaurants")
    fun listPage(@Query("_page") page: Int): Call<ApiGetRestaurants>

    @GET("restaurants/{id}/menu")
    fun listItemsMenu(@Path("id") number: String): Call<List<ApiItemMenu>>

    @GET("reviews")
    fun listReviews(@Query("_page") page: Int): Call<ApiReviews>

    @FormUrlEncoded
    @POST("reviews")
    fun postReview(@Header("authorization") auth: String,
                   @Field("user") user: String,
                   @Field("date") date: String,
                   @Field("rating") rating: Double,
                   @Field("comments") comments: String,
                   @Field("restaurant") restaurant: String): Call<String>
}