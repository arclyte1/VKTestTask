package com.example.vktesttask.presentation.gif_list

import com.example.vktesttask.domain.model.Gif

data class GifListItemVo(
    val id: String,
    val url: String,
    val heightRatio: Double,
) {

    companion object {
        fun fromGif(gif: Gif): GifListItemVo {
            return GifListItemVo(
                id = gif.id,
                url = gif.downsizedImageUrl,
                heightRatio = gif.downsizedImageHeightRatio,
            )
        }
    }
}