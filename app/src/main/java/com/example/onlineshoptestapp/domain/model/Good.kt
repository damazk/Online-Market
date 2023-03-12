package com.example.onlineshoptestapp.domain.model

import android.net.Uri

data class Good(
    val category: String? = null,
    val name: String? = null,
    val price: Double? = null,
    val discount: Int? = null,
    val imageUrl: String? = null
)
