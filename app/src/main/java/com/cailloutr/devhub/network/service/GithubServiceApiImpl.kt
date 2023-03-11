package com.cailloutr.devhub.network.service

import com.cailloutr.devhub.network.model.User
import retrofit2.Response
import javax.inject.Inject


class GithubServiceApiImpl @Inject constructor(
    private val githubServiceApi: GithubServiceApi
) : GithubServiceApiHelper {
    override suspend fun getUser(username: String): Response<User> {
        return githubServiceApi.getUser(username)
    }
}