package com.example.vktesttask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gif(
    val id: String,
    val url: String,
    val downsizedImageUrl: String,
    val downsizedImageHeightRatio: Double,
    val originalImageUrl: String,
    val title: String?,
    val user: User?,
) : Parcelable