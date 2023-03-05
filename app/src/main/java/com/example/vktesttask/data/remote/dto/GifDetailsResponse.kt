package com.example.vktesttask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GifDetailsResponse(
    @SerializedName("data") val data: GifDto,
)