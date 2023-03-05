package com.example.vktesttask.domain.usecase

import com.example.vktesttask.common.Resource
import com.example.vktesttask.domain.repository.GifRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGifDetailsUseCase @Inject constructor(
    private val repository: GifRepository
) {

    operator fun invoke(gifId: String) = flow{
        try {
            emit(Resource.Loading())
            val response = repository.getGifDetails(gifId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}