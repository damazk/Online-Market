package com.example.onlineshoptestapp.data.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.onlineshoptestapp.data.local.OnlineShopDatabase
import com.example.onlineshoptestapp.data.local.UserEntity
import com.example.onlineshoptestapp.data.mappers.toGood
import com.example.onlineshoptestapp.data.mappers.toUser
import com.example.onlineshoptestapp.data.mappers.toUserEntity
import com.example.onlineshoptestapp.data.remote.GoodApi
import com.example.onlineshoptestapp.domain.model.Good
import com.example.onlineshoptestapp.domain.model.User
import com.example.onlineshoptestapp.domain.repository.OnlineShopRepository
import com.example.onlineshoptestapp.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnlineShopRepositoryImpl @Inject constructor(
    private val api: GoodApi,
    private val db: OnlineShopDatabase
) : OnlineShopRepository {

    private val dao = db.dao

    override suspend fun getLatestGoods(): Flow<Response<List<Good>>> {
        return flow {
            emit(Response.Loading())
            try {
                val goods = api.getLatestGoods().goods.map { it.toGood() }
                Log.d("GetLatestGoodsData", "Success! Retrieved ${goods.size} elements.")
                emit(Response.Success(goods))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("GetLatestGoodsData", "Failed: ${e.message}")
                emit(Response.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getFlashGoods(): Flow<Response<List<Good>>> {
        return flow {
            emit(Response.Loading())
            try {
                val goods = api.getFlashGoods().goods.map { it.toGood() }
                Log.d("GetFlashGoodsData", "Success! Retrieved ${goods.size} elements.")
                emit(Response.Success(goods))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("GetFlashGoodsData", "Failed: ${e.message}")
                emit(Response.Error(e.message.toString()))
            }
        }
    }

    override suspend fun createUser(firstName: String, lastName: String, email: String): Flow<Response<User>> {
        return flow {
            emit(Response.Loading())
            try {
                val user = UserEntity(firstName, lastName, email)
                dao.addUser(user)
                Log.d("CreateUser", "Success! User created.")
                emit(Response.Success(user.toUser()))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("CreateUser", "Failed: ${e.message}")
                emit(Response.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getUserByFirstName(firstName: String): Flow<Response<User?>> {
        return flow {
            emit(Response.Loading())
            try {
                val user = dao.getUserByFirstName(firstName)
                if (user != null) {
                    Log.d("GetUserByFirstName", "Success! Got user $user.")
                    emit(Response.Success(user.toUser()))
                } else {
                    throw NullPointerException("User is null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("GetUserByFirstName", "Failed: ${e.message}")
                emit(Response.Error(e.message.toString()))
            }
        }
    }

    override suspend fun deleteUser(user: User): Boolean {
        return try {
            dao.logOutUser(user.toUserEntity())
            Log.d("LogOutUser", "Success!")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("LogOutUser", "Failed: ${e.message}")
            false
        }
    }

    override suspend fun logInUser(sharedPref: SharedPreferences?, user: User?): Boolean {
        return try {
            sharedPref?.edit()?.apply {
                putString("firstName", user?.firstName)
                putString("lastName", user?.lastName)
                putString("email", user?.email)
                putString("profilePhotoUrl", user?.profilePhotoUrl)
                apply()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("LogInUser", "Failed: ${e.message}")
            false
        }
    }

    override suspend fun logOutUser(sharedPref: SharedPreferences?): Boolean {
        return try {
            sharedPref?.edit()?.clear()
            sharedPref?.edit()?.apply {
                remove("firstName")
                remove("lastName")
                remove("email")
                remove("profilePhotoUrl")
                apply()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("LogOutUser", "Failed: ${e.message}")
            false
        }
    }

    override suspend fun getCurrentUserData(sharedPref: SharedPreferences?, dataType: String): Any? {
        return try {
            val data = sharedPref?.getString(dataType, "Not found")!!
            data
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("GetCurrentUserData", "Failed: ${e.message}")
            null
        }
    }
}