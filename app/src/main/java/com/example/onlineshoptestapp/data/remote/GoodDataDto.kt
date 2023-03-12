package com.example.onlineshoptestapp.data.remote

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoodDataDto(
    @Json(name = "category") val category: String,
    @Json(name = "name") val name: String,
    @Json(name = "price") val price: Double,
    @Json(name = "discount") val discount: Int? = null,
    @Json(name = "image_url") val imageUrl: String
)