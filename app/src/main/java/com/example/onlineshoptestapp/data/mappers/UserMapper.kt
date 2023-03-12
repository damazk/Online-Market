package com.example.onlineshoptestapp.data.mappers

import com.example.onlineshoptestapp.data.local.UserEntity
import com.example.onlineshoptestapp.domain.model.User

fun User.toUserEntity(): UserEntity {
    val firstName = this.firstName
    val lastName = this.lastName
    val email = this.email
    val profilePhotoUrl = this.profilePhotoUrl
    return UserEntity(firstName, lastName, email, profilePhotoUrl)
}

fun UserEntity.toUser(): User {
    val firstName = this.firstName
    val lastName = this.lastName
    val email = this.email
    val profilePhotoUrl = this.profilePhotoUrl
    return User(firstName, lastName, email, profilePhotoUrl)
}