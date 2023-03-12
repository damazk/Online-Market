package com.example.onlineshoptestapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatestGooddDto(
    @Json(name = "latest")
    val goods: List<GoodDataDto>
)

@JsonClass(generateAdapter = true)
data class FlashGoodsDto(
    @Json(name = "flash_sale")
    val goods: List<GoodDataDto>
)
