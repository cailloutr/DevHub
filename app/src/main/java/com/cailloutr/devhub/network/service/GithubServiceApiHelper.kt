package com.cailloutr.devhub.network.service

import com.cailloutr.devhub.network.model.User
import retrofit2.Response

interface GithubServiceApiHelper {
    suspend fun getUser(username: String): Response<User>
}