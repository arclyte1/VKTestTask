package com.example.vktesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GifListResponse(
    @SerializedName("data") val data: List<GifDto>,
)
