package com.example.vktesttask.domain.repository

import androidx.paging.PagingData
import com.example.vktesttask.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifRepository {

    suspend fun getTrending(): Flow<PagingData<Gif>>
}