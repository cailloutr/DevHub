package com.cailloutr.devhub.network.service

import com.cailloutr.devhub.network.model.GithubUser
import com.cailloutr.devhub.network.model.GithubUserRepositories
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GithubServiceApiHelper {
    suspend fun getUserInformation(username: String): Flow<Response<GithubUser>>

    suspend fun getUsersRepositories(username: String): Flow<Response<List<GithubUserRepositories>>>
}