package com.example.onlineshoptestapp.domain.repository

import com.example.onlineshoptestapp.data.repository.OnlineShopRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOnlineShopRepository(
        onlineShopRepositoryImpl: OnlineShopRepositoryImpl
    ): OnlineShopRepository
}