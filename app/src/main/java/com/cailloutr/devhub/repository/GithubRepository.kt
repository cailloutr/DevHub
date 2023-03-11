package com.cailloutr.devhub.repository

import com.cailloutr.devhub.network.service.GithubServiceApiHelper
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubServiceApiHelper: GithubServiceApiHelper
) {
    suspend fun getUser(username: String) = githubServiceApiHelper.getUser(username)
}