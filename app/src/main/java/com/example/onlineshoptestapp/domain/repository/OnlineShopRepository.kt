package com.example.onlineshoptestapp.domain.repository

import android.app.Activity
import android.content.SharedPreferences
import com.example.onlineshoptestapp.domain.model.Good
import com.example.onlineshoptestapp.domain.model.User
import com.example.onlineshoptestapp.util.Response
import kotlinx.coroutines.flow.Flow

interface OnlineShopRepository {

    suspend fun getLatestGoods(): Flow<Response<List<Good>>>

    suspend fun getFlashGoods(): Flow<Response<List<Good>>>

    suspend fun createUser(firstName: String, lastName: String, email: String): Flow<Response<User>>

    suspend fun logInUser(sharedPref: SharedPreferences?, user: User?) : Boolean

    suspend fun logOutUser(sharedPref: SharedPreferences?) : Boolean

    suspend fun getCurrentUserData(sharedPref: SharedPreferences?, dataType: String): Any?

    suspend fun getUserByFirstName(firstName: String): Flow<Response<User?>>

    suspend fun deleteUser(user: User): Boolean
}