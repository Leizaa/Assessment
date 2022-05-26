package com.example.assessment.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assessment.model.ResponseResult
import com.example.assessment.model.SearchResponse
import com.example.assessment.repository.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _response: MutableLiveData<ResponseResult<SearchResponse>> = MutableLiveData()
    val response: LiveData<ResponseResult<SearchResponse>> = _response

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun doSearchUser(username: String) = launch {
        _response.value = ResponseResult.Loading(true)
        _response.value = searchUseCase.execute(username)
        _response.value = ResponseResult.Loading(false)
    }
}