package com.example.vktesttask.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.vktesttask.common.Constants
import com.example.vktesttask.data.remote.GiphyApi
import com.example.vktesttask.domain.model.Gif
import com.example.vktesttask.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
    private val api: GiphyApi
): GifRepository {

    private val pagingConfig = PagingConfig(
        pageSize = Constants.GIF_PAGE_SIZE,
        initialLoadSize = Constants.GIF_PAGE_SIZE,
        enablePlaceholders = false
    )

    override fun getTrending(): Flow<PagingData<Gif>> {
        return search(null)
    }

    override fun search(searchQuery: String?): Flow<PagingData<Gif>> {
        Log.d("Repository", searchQuery.toString())
        return Pager(
            pagingConfig
        ) {
            GifPagingSource(api, searchQuery)
        }.flow.map { pagingData ->
            pagingData.map { gifDto ->
                gifDto.toGif()
            }
        }
    }
}