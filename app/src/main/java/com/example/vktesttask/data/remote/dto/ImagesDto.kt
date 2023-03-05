package com.example.vktesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ImagesDto(
    @SerializedName("fixed_height") val downsizedImage: ImageDto,
    @SerializedName("original") val originalImage: ImageDto,
)