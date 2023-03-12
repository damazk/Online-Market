package com.example.onlineshoptestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class OnlineShopDatabase: RoomDatabase() {
    abstract val dao: OnlineShopDao
}