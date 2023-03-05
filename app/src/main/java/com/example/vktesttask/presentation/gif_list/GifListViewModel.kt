package com.example.vktesttask.presentation.gif_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.vktesttask.domain.model.Gif
import com.example.vktesttask.domain.usecase.GetTrendingGifsUseCase
import com.example.vktesttask.domain.usecase.SearchGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifListViewModel @Inject constructor(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
    private val searchGifsUseCase: SearchGifsUseCase,
): ViewModel() {

    private val trendingGifFlow: Flow<PagingData<Gif>> = getTrendingGifsUseCase().cachedIn(viewModelScope)

    var gifFlowFlow: MutableStateFlow<Flow<PagingData<Gif>>>
        private set

    init {
        gifFlowFlow = MutableStateFlow(trendingGifFlow)
    }

    private var searchJob: Job? = null

    fun searchTextChanged(text: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            searchText(text)
        }
    }

    private fun searchText(text: String) {
        searchJob?.cancel()
        gifFlowFlow.value = if (text == "") {
            trendingGifFlow
        } else {
            searchGifsUseCase(text).cachedIn(viewModelScope)
        }
    }

    companion object {
        const val DEBOUNCE_DELAY = 500L
    }
}