package com.cailloutr.devhub.network.service

import com.cailloutr.devhub.network.model.GithubUserRepositories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class GithubServiceApiImpl @Inject constructor(
    private val githubServiceApi: GithubServiceApi
) : GithubServiceApiHelper {
    override suspend fun getUserInformation(username: String) = flow {
        emit(githubServiceApi.getUserInformation(username))
    }

    override suspend fun getUsersRepositories(
        username: String
    ): Flow<Response<List<GithubUserRepositories>>> = flow {
        emit(githubServiceApi.getUsersRepositories(username))
    }
}