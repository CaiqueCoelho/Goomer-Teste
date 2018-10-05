package com.example.caique.goomer.entity

import com.google.gson.annotations.SerializedName

class PaginationReviews (
        @SerializedName("self") val self: String,
        @SerializedName("next") val next: String?
)