package com.example.onlineshoptestapp.data.mappers

import com.example.onlineshoptestapp.data.remote.GoodDataDto
import com.example.onlineshoptestapp.domain.model.Good

fun GoodDataDto.toGood(): Good {
    val category = this.category
    val name = this.name
    val price = this.price
    val discount = this.discount
    val imageUrl = this.imageUrl
    return Good(category, name, price, discount, imageUrl)
}