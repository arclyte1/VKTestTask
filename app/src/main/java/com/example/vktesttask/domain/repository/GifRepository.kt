package com.example.vktesttask.domain.repository

import androidx.paging.PagingData
import com.example.vktesttask.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifRepository {

    fun getTrending(): Flow<PagingData<Gif>>

    fun search(searchQuery: String?): Flow<PagingData<Gif>>
}