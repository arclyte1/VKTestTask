package com.example.vktesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    @SerializedName("data") val data: List<GifDto>,
)
