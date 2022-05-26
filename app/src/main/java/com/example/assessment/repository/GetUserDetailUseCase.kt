package com.example.assessment.repository

import com.example.assessment.model.ResponseResult
import com.example.assessment.model.UserDetailResponse
import javax.inject.Inject

interface GetUserDetailUseCase {
    suspend fun execute(username: String): ResponseResult<UserDetailResponse>
}

class GetUserDetailUseCaseImplementation @Inject constructor(
    private val repository: UserRepository
) : GetUserDetailUseCase {
    override suspend fun execute(username: String): ResponseResult<UserDetailResponse> {
        return repository.getUserDetail(username)
    }
}