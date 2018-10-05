package com.example.caique.goomer.entity

import com.google.gson.annotations.SerializedName

class ApiItemMenu (
    @SerializedName("_id") val id: String,
    @SerializedName("imagePath") val imagePath: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String
    )