package com.cailloutr.devhub.network.model

import com.cailloutr.devhub.model.GithubRepositoryModel
import com.squareup.moshi.Json

data class GithubUserRepositories(
    val name: String = "",
    val description: String = "",
    @field:Json(name = "html_url")
    val url: String,
)

fun GithubUserRepositories.toGithubRepositoryModel() = GithubRepositoryModel(
    name = name ?: "",
    description = description ?: "",
    url = url ?: ""
)
