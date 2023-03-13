package com.cailloutr.devhub.network.service

import com.cailloutr.devhub.network.model.GithubUser
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GithubServiceApiHelper {
    suspend fun getUser(username: String): Flow<Response<GithubUser>>
}