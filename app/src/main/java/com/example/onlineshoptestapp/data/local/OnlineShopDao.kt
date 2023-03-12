package com.example.onlineshoptestapp.data.local

import androidx.room.*

@Dao
interface OnlineShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity)

    @Delete
    suspend fun logOutUser(user: UserEntity)

    @Query("SELECT * FROM userentity WHERE first_name LIKE :firstName LIMIT 1")
    suspend fun getUserByFirstName(firstName: String): UserEntity?
}