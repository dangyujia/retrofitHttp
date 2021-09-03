package com.rain.rain.retrofit.http.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 *@Author: Rain
 *@Date: 2021/8/23 16:40
 *@Description: viewModel 基类
 */
open class BaseViewModel : ViewModel() {

    /**
     * 网络请求直接通过flow 下发数据
     */
    fun <T : Any> Deferred<T>.request(_sharedFlow: MutableSharedFlow<Result<T>>) =
        flow {
            emit(this@request.await())
        }.catch { t: Throwable ->
            _sharedFlow.emit(Result.Error(t))
        }.onStart {
            _sharedFlow.emit(Result.Loading(isLoading = true))
        }.flowOn(Dispatchers.IO).onCompletion {
            _sharedFlow.emit(Result.Loading(isLoading = false))
        }


    /**
     * 生成 Result 后 通过flow 发送数据
     *  跟Deferred<T>.request() 区别 ：
     *  Result<T>.request() 是在Repository 里面直接验证网络返回是否正确即：this@request 可能返回的是异常状态
     *  Deferred<T>.request() 是通过flow的catch 捕获网络状态  网络状态 待验证
     */
    fun <T : Any> Result<T>.request(_sharedFlow: MutableSharedFlow<Result<T>>) =
        flow {
            emit(this@request)
        }.onStart {
            _sharedFlow.emit(Result.Loading(isLoading = true))
        }.flowOn(Dispatchers.IO).onCompletion {
            _sharedFlow.emit(Result.Loading(isLoading = false))
        }


    /**
     *  Post 慎用 stateFlow  这个会开始发送一个消息
     */
    fun <T : Any> Deferred<T>.request(_stateFlow: MutableStateFlow<Result<T>>) =
        flow {
            emit(this@request.await())
        }.catch { t: Throwable ->
            _stateFlow.value = Result.Error(t)
        }.onStart {
            _stateFlow.value = Result.Loading(isLoading = true)
        }.flowOn(Dispatchers.IO).onCompletion {
            _stateFlow.value = Result.Loading(isLoading = false)
        }


    /**
     *  Post 慎用 stateFlow  这个会开始发送一个消息
     */
    fun <T : Any> Result<T>.request(_stateFlow: MutableStateFlow<Result<T>>) =
        flow {
            emit(this@request)
        }.onStart {
            _stateFlow.value = Result.Loading(isLoading = true)
        }.flowOn(Dispatchers.IO).onCompletion {
            _stateFlow.value = Result.Loading(isLoading = false)
        }


    /**
     * 获取数据库数据 和 网络请求
     */
    @ExperimentalCoroutinesApi
    fun <T : Any> request(
        results: List<Result<T>>,
        start: suspend (Result<T>) -> Unit,
        each: suspend (Result<T>) -> Unit,
        completion: suspend (Result<T>) -> Unit
    ) = results.map { item ->
        flow { emit(item) }
    }.merge().onStart {
        start(Result.Loading(isLoading = true))
    }.onEach { result ->
        each(result)
    }.flowOn(Dispatchers.IO).onCompletion {
        completion(Result.Loading(isLoading = false))
    }
}