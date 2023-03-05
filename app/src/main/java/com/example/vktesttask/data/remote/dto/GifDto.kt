package com.example.vktesttask.data.remote.dto

import com.example.vktesttask.domain.model.Gif
import com.google.gson.annotations.SerializedName

data class GifDto(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("images") val images: ImagesDto,
    @SerializedName("title") val title: String?,
    @SerializedName("user") val user: UserDto?,
) {

    fun toGif() : Gif {
        return Gif(
            id = id,
            url = url,
            downsizedImageUrl = images.downsizedImage.url ?: "",
            downsizedImageHeightRatio = if (
                images.downsizedImage.width != null &&
                images.downsizedImage.height != null
            ) {
                images.downsizedImage.height.toDouble() / images.downsizedImage.width.toDouble()
            } else 2.0,
            originalImageUrl = images.originalImage.url ?: "",
            title = title,
            user = user?.toUser(),
        )
    }
}