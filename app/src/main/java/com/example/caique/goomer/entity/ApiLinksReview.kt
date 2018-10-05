package com.example.caique.goomer.entity

import com.google.gson.annotations.SerializedName

class ApiLinksReview (
        @SerializedName("self") val self: String,
        @SerializedName("restaurant") val restaurant: String?
)