package com.example.vktesttask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val avatarUrl: String,
    val username: String,
    val displayName: String,
    val isVerified: Boolean,
) : Parcelable