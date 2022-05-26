package com.example.assessment.repository

import com.example.assessment.model.ResponseResult
import com.example.assessment.model.UserRepoResponse
import javax.inject.Inject

interface GetRepoUseCase {
    suspend fun execute(username: String): ResponseResult<List<UserRepoResponse>>
}

class GetRepoUseCaseImplementation @Inject constructor(
    private val repository: UserRepository
) : GetRepoUseCase {
    override suspend fun execute(username: String): ResponseResult<List<UserRepoResponse>> {
        return repository.getUserRepositoryList(username)
    }
}