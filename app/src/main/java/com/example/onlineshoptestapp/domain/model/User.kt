package com.example.onlineshoptestapp.domain.model

import android.net.Uri

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePhotoUrl: String? = null
)
