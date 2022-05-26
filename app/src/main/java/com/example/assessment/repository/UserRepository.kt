package com.example.assessment.repository

import com.example.assessment.model.ResponseResult
import com.example.assessment.model.SearchResponse
import com.example.assessment.model.UserDetailResponse
import com.example.assessment.model.UserRepoResponse
import com.example.assessment.utility.FunctionalExtension.await
import javax.inject.Inject

interface UserRepository {
    suspend fun searchUserList(username: String): ResponseResult<SearchResponse>
    suspend fun getUserDetail(username: String): ResponseResult<UserDetailResponse>
    suspend fun getUserRepositoryList(username: String): ResponseResult<List<UserRepoResponse>>
}

class UserRepositoryImplementation @Inject constructor(
    private val remoteSource: UserRemoteSource
) : UserRepository {
    override suspend fun searchUserList(username: String): ResponseResult<SearchResponse> {
        return await { remoteSource.searchUserList(username) }
    }

    override suspend fun getUserDetail(username: String): ResponseResult<UserDetailResponse> {
        return await { remoteSource.getUserDetail(username) }
    }

    override suspend fun getUserRepositoryList(username: String): ResponseResult<List<UserRepoResponse>> {
        return await { remoteSource.getUserRepositoryList(username) }
    }
}