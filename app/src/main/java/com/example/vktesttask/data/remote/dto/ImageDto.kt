package com.example.vktesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("url") val url: String?,
    @SerializedName("width") val width: String?,
    @SerializedName("height") val height: String?,
)