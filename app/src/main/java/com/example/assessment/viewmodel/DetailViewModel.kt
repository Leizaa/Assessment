package com.example.assessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assessment.model.ResponseResult
import com.example.assessment.model.UserDetailResponse
import com.example.assessment.model.UserRepoResponse
import com.example.assessment.repository.GetRepoUseCase
import com.example.assessment.repository.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getRepoUseCase: GetRepoUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase
) :ViewModel(), CoroutineScope{

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _userDetailResponse: MutableLiveData<ResponseResult<UserDetailResponse>> = MutableLiveData()
    val userDetailResponse: LiveData<ResponseResult<UserDetailResponse>> = _userDetailResponse

    private val _userRepoResponse: MutableLiveData<ResponseResult<List<UserRepoResponse>>> =
        MutableLiveData()
    val userRepositoryResponse: LiveData<ResponseResult<List<UserRepoResponse>>> =
        _userRepoResponse

    fun doGetUserDetail(username: String) = launch {
        _userDetailResponse.value = ResponseResult.Loading(true)
        _userDetailResponse.value = getUserDetailUseCase.execute(username)
        _userDetailResponse.value = ResponseResult.Loading(false)
    }

    fun doGetRepo(username: String) = launch {
        _userRepoResponse.value = ResponseResult.Loading(true)
        _userRepoResponse.value = getRepoUseCase.execute(username)
        _userRepoResponse.value = ResponseResult.Loading(false)
    }

}