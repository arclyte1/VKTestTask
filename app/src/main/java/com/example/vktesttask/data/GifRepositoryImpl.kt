package com.example.vktesttask.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.vktesttask.common.Constants
import com.example.vktesttask.domain.model.Gif
import com.example.vktesttask.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
    private val gifPagingSource: GifPagingSource
): GifRepository {

    private val pagingConfig = PagingConfig(
        pageSize = Constants.GIF_PAGE_SIZE,
        initialLoadSize = Constants.GIF_PAGE_SIZE,
        enablePlaceholders = true
    )

    override suspend fun getTrending(): Flow<PagingData<Gif>> {
        return Pager(
            pagingConfig
        ) {
            gifPagingSource
        }.flow.map { pagingData ->
            pagingData.map { gifDto ->
                gifDto.toGif()
            }
        }
    }
}