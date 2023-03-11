package com.cailloutr.devhub.network.model

import com.squareup.moshi.Json

data class User(
    val login: String,
    val id: Long,
    @field:Json(name = "avatar_url")
    val avatarUrl: String,
    val name: String,
    val bio: String
)