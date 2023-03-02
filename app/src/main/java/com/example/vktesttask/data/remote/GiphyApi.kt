package com.example.vktesttask.data.remote

import com.example.vktesttask.data.remote.dto.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("gifs/trending")
    suspend fun getTrending(
        @Query("api_key") apiKey: String,
        @Query("rating") rating: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ) : TrendingResponse
}