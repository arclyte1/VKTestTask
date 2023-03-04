package com.example.vktesttask.domain.usecase

import com.example.vktesttask.domain.repository.GifRepository
import javax.inject.Inject

class SearchGifsUseCase @Inject constructor(
    private val repository: GifRepository
) {

    operator fun invoke(searchQuery: String) = repository.search(searchQuery)
}