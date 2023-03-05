package com.example.vktesttask.data.remote.dto

import com.example.vktesttask.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("username") val username: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("is_verified") val isVerified: Boolean,
) {

    fun toUser(): User {
        return User(
            avatarUrl = avatarUrl,
            username = username,
            displayName = displayName,
            isVerified = isVerified,
        )
    }
}