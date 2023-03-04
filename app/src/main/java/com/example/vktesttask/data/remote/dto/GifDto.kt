package com.example.vktesttask.data.remote.dto

import com.example.vktesttask.domain.model.Gif
import com.google.gson.annotations.SerializedName

data class GifDto(
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: ImagesDto,
) {

    fun toGif() : Gif {
        return Gif(
            id = id,
            downsizedImageUrl = images.downsizedImage.url ?: "",
            downsizedImageHeightRatio = if (
                images.downsizedImage.width != null &&
                images.downsizedImage.height != null
            ) {
                images.downsizedImage.height.toDouble() / images.downsizedImage.width.toDouble()
            } else 2.0
        )
    }
}