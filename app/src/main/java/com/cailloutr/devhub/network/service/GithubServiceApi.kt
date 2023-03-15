package com.cailloutr.devhub.network.service

import com.cailloutr.devhub.network.model.GithubUser
import com.cailloutr.devhub.network.model.GithubUserRepositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubServiceApi {

    @GET("users/{username}")
    suspend fun getUserInformation(@Path("username") username: String): Response<GithubUser>

    @GET("users/{username}/repos")
    suspend fun getUsersRepositories(@Path("username") username: String): Response<List<GithubUserRepositories>>

}
