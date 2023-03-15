package com.cailloutr.devhub.repository

import com.cailloutr.devhub.network.model.GithubUser
import com.cailloutr.devhub.network.model.GithubUserRepositories
import com.cailloutr.devhub.network.service.GithubServiceApiHelper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubServiceApiHelper: GithubServiceApiHelper
) {
    suspend fun getUser(username: String): Flow<Response<GithubUser>> =
        githubServiceApiHelper.getUserInformation(username)

    suspend fun getUsersRepositories(username: String) : Flow<Response<List<GithubUserRepositories>>> =
        githubServiceApiHelper.getUsersRepositories(username)

}