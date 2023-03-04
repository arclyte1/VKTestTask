package com.example.vktesttask.presentation.gif_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.vktesttask.domain.usecase.GetTrendingGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifListViewModel @Inject constructor(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase
): ViewModel() {

    val gifFlow: Flow<PagingData<GifListItemVo>> = getTrendingGifsUseCase().map {  data ->
        data.map { gif ->
            GifListItemVo.fromGif(gif)
        }
    }.cachedIn(viewModelScope)

}