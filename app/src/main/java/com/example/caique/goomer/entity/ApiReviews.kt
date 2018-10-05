package com.example.caique.goomer.entity

import com.google.gson.annotations.SerializedName

class ApiReviews (
        @SerializedName("_links") val self: PaginationReviews,
        @SerializedName("items") val reviews: List<ApiItemReview>?
)