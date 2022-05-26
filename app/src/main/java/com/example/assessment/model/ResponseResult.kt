package com.example.assessment.model

sealed class ResponseResult<out T> {
    data class Error(val httpStatus: Int) : ResponseResult<Nothing>()
    data class Success<T>(val data: T?) : ResponseResult<T> ()
    data class Loading(val isLoading: Boolean) : ResponseResult<Nothing>()
}