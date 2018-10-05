package com.example.caique.goomer

import com.google.gson.annotations.SerializedName

class PaginationRestaurants (
    @SerializedName("self") val self: String,
    @SerializedName("next") val next: String?
    )