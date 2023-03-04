package com.example.vktesttask.data.remote

import com.example.vktesttask.data.remote.dto.GifListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("gifs/trending")
    suspend fun getTrending(
        @Query("api_key") apiKey: String,
        @Query("rating") rating: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ) : GifListResponse

    @GET("gifs/search")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("rating") rating: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("q") searchQuery: String,
    ) : GifListResponse
}