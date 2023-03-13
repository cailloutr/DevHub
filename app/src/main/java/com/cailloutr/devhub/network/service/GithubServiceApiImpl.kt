package com.cailloutr.devhub.network.service

import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GithubServiceApiImpl @Inject constructor(
    private val githubServiceApi: GithubServiceApi
) : GithubServiceApiHelper {
    override suspend fun getUser(username: String) = flow {
        emit(githubServiceApi.getUser(username))
    }

}