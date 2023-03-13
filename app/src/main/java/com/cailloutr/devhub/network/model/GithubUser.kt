package com.cailloutr.devhub.network.model

import com.cailloutr.devhub.ui.ProfileUiState
import com.squareup.moshi.Json

data class GithubUser(
    val login: String,
    val id: Long,
    @field:Json(name = "avatar_url")
    val avatarUrl: String,
    val name: String,
    val bio: String
)

fun GithubUser.toProfitleUiState() = ProfileUiState(
    username = login,
    id = id,
    profileImage = avatarUrl,
    name = name,
    bio = bio
)