package com.example.caique.goomer.entity

import com.google.gson.annotations.SerializedName

class ApiItemReview (
        @SerializedName("_id") val self: String,
        @SerializedName("user") val user: String?,
        @SerializedName("date") val date: String?,
        @SerializedName("rating") val rating: String?,
        @SerializedName("comments") val comments: String?,
        @SerializedName("restaurant") val restaurant: String?,
        @SerializedName("__v") val v: String?
)