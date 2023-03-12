package com.example.onlineshoptestapp.di

import android.app.Application
import androidx.room.Room
import com.example.onlineshoptestapp.data.local.OnlineShopDatabase
import com.example.onlineshoptestapp.data.remote.GoodApi
import com.example.onlineshoptestapp.domain.model.Good
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

    @Provides
    @Singleton
    fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
        .addLast(kotlinJsonAdapterFactory)
        .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun provideGoodApi(
        okHttp: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ) : GoodApi {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(moshiConverterFactory)
            .client(okHttp)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): OnlineShopDatabase {
        return Room.databaseBuilder(
            app,
            OnlineShopDatabase::class.java,
            "onlineshopdatabase.db"
        ).build()
    }
}