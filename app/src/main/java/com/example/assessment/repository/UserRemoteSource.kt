package com.example.assessment.repository

import com.example.assessment.model.SearchResponse
import com.example.assessment.model.UserDetailResponse
import com.example.assessment.model.UserRepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserRemoteSource {
    @GET("search/users")
    suspend fun searchUserList(@Query("q") username: String): Response<SearchResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): Response<UserDetailResponse>

    @GET("users/{username}/repos")
    suspend fun getUserRepositoryList(@Path("username") username: String): Response<List<UserRepoResponse>>
}