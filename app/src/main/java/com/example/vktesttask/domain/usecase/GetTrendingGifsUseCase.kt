package com.example.vktesttask.domain.usecase

import com.example.vktesttask.domain.repository.GifRepository
import javax.inject.Inject

class GetTrendingGifsUseCase @Inject constructor(
    private val repository: GifRepository
) {

    suspend operator fun invoke() = repository.getTrending()
}