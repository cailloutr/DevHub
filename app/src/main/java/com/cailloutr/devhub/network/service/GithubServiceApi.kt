package com.cailloutr.devhub.network.service

import com.cailloutr.devhub.network.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubServiceApi {

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<User>
}
