package com.example.vktesttask.presentation.gif_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktesttask.common.Resource
import com.example.vktesttask.domain.model.Gif
import com.example.vktesttask.domain.usecase.GetGifDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GifDetailsViewModel @Inject constructor(
    private val getGifDetailsUseCase: GetGifDetailsUseCase
) : ViewModel() {

    private val _gif = MutableStateFlow<Gif?>(null)
    val gif: StateFlow<Gif?> = _gif

    fun getGifDetails(gifId: String) {
        getGifDetailsUseCase(gifId).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _gif.value = null
                }
                is Resource.Success -> {
                    _gif.value = resource.data
                }
                is Resource.Error -> {
                    _gif.value = null
                }
            }
        }.launchIn(viewModelScope)
    }
}