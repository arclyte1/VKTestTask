package com.example.vktesttask.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vktesttask.common.Constants
import com.example.vktesttask.data.remote.GiphyApi
import com.example.vktesttask.data.remote.dto.GifDto
import javax.inject.Inject

class GifPagingSource @Inject constructor(
    private val giphyApi: GiphyApi
) : PagingSource<Int, GifDto>() {

    override fun getRefreshKey(state: PagingState<Int, GifDto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifDto> {
        val pageIndex = params.key ?: 0
        try {
            val gifs = giphyApi.getTrending(
                apiKey = Constants.GIPHY_API_KEY,
                rating = "g",
                limit = params.loadSize,
                offset = params.loadSize * pageIndex
            ).data

            val nextKey =
                if (gifs.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }

            return LoadResult.Page(
                data = gifs,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}