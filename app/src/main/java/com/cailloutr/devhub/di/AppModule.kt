package com.cailloutr.devhub.di

import com.cailloutr.devhub.BuildConfig
import com.cailloutr.devhub.network.service.GithubServiceApi
import com.cailloutr.devhub.network.service.GithubServiceApiHelper
import com.cailloutr.devhub.network.service.GithubServiceApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun providesBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): GithubServiceApi =
        retrofit.create(GithubServiceApi::class.java)

    @Provides
    @Singleton
    fun provideGithubApiHelper(apiHelper: GithubServiceApiImpl): GithubServiceApiHelper =
        apiHelper

}