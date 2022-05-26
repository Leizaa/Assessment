package com.example.assessment.repository

import com.example.assessment.model.ResponseResult
import com.example.assessment.model.SearchResponse
import javax.inject.Inject

interface SearchUseCase {
    suspend fun execute(username: String): ResponseResult<SearchResponse>
}

class SearchUseCaseImplementation @Inject constructor(
    private val repository: UserRepository
) : SearchUseCase {

    override suspend fun execute(username: String): ResponseResult<SearchResponse> {
        return repository.searchUserList(username)
    }

}