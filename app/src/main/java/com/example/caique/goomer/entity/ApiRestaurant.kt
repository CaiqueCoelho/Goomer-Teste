package com.example.caique.goomer.entity

import com.google.gson.annotations.SerializedName

class ApiRestaurant(
    @SerializedName("self") val self: String,
    @SerializedName("menu") val menu: String,
    @SerializedName("_id") val idKey: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("deliveryEstimate") val deliveryEstimate: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("imagePath") val imagePath: String,
    @SerializedName("about") val about: String,
    @SerializedName("hours") val hours: String,
    @SerializedName("_v") val _v: String
    )