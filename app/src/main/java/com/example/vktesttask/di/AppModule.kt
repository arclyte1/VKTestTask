package com.example.vktesttask.di

import com.example.vktesttask.common.Constants
import com.example.vktesttask.data.GifRepositoryImpl
import com.example.vktesttask.data.remote.GiphyApi
import com.example.vktesttask.domain.repository.GifRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGiphyApi() : GiphyApi {
        return Retrofit.Builder()
            .baseUrl(Constants.GIPHY_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGifRepository(api: GiphyApi): GifRepository {
        return GifRepositoryImpl(api)
    }
}