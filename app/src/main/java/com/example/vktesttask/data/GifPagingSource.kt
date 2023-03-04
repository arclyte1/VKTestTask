package com.example.vktesttask.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vktesttask.common.Constants
import com.example.vktesttask.data.remote.GiphyApi
import com.example.vktesttask.data.remote.dto.GifDto
import javax.inject.Inject

class GifPagingSource(
    private val giphyApi: GiphyApi,
    private val searchQuery: String? = null,
) : PagingSource<Int, GifDto>() {

    override fun getRefreshKey(state: PagingState<Int, GifDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifDto> {
        val pageIndex = params.key ?: 0
        try {
            Log.d("PagingSource", searchQuery.toString())
            val gifs = if (searchQuery != null)
                giphyApi.search(
                    apiKey = Constants.GIPHY_API_KEY,
                    rating = "g",
                    limit = params.loadSize,
                    offset = params.loadSize * pageIndex,
                    searchQuery = searchQuery,
                ).data
            else giphyApi.getTrending(
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
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}