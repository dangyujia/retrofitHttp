package com.rain.rain.retrofit.http.test

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.rain.rain.retrofit.http.base.Result
import com.rain.rain.retrofit.http.base.BaseViewModel
import com.rain.rain.retrofit.moudlea.PinkerConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 *@Author: Rain
 *@Date: 2021/8/30 13:50
 *@Description: retrofitHttp
 */
class Test2ViewModel : BaseViewModel() {

    private val _stateFlow =
        MutableStateFlow<Result<MutableList<PinkerConfig>>>(Result.Loading(isLoading = false)) //仅在值已更新且不相同值时返回
    val stateFlow: StateFlow<Result<MutableList<PinkerConfig>>>
        get() = _stateFlow


    private val _sharedFlow1 = MutableSharedFlow<Result<MutableList<PinkerConfig>>>() // 都返回
    val sharedFlow1: SharedFlow<Result<MutableList<PinkerConfig>>>
        get() = _sharedFlow1


    private val _sharedFlow3 = MutableSharedFlow<Result<MutableList<PinkerConfig>>>() // 都返回
    val sharedFlow3: SharedFlow<Result<MutableList<PinkerConfig>>>
        get() = _sharedFlow3


    private val _shareFlow =
        MutableStateFlow<Result<MutableList<PinkerConfig>>>(Result.Loading(isLoading = false))
    val shareFlow: StateFlow<Result<MutableList<PinkerConfig>>>
        get() = _shareFlow


    private val _sharedFlow2 = MutableSharedFlow<Result<Pinker>>() // 都返回
    val sharedFlow2: SharedFlow<Result<Pinker>>
        get() = _sharedFlow2


    private val _shareFlowLogin = MutableSharedFlow<Result<UserData>>()
    val shareFlowLogin: SharedFlow<Result<UserData>>
        get() = _shareFlowLogin


    fun get1() {
        viewModelScope.launch {
            Test2Repository.getNetList().request(_stateFlow).collect { result ->
                _stateFlow.value = result
            }
        }
    }

    fun get2() {
        viewModelScope.launch {
            Test2Repository.getNetList().request(_sharedFlow3).collect { result ->
                _sharedFlow3.emit(result)
            }
        }
//        viewModelScope.launch {
//            Test2Repository.getListAsync().request(_sharedFlow1).collect { result ->
//                _sharedFlow1.emit(Result.Success(result))
//            }
//        }
    }

    //merge 合并多个流成 一个流。 可以用在 多级缓存加载上 无序发送
    @ExperimentalCoroutinesApi
    fun get() {
        viewModelScope.launch {
            val list = listOf(
                Test2Repository.getDBList(),
                Test2Repository.getNetList()
            )
            request(results = list, start = { result ->
                _shareFlow.value = result
            }, each = { result ->
                if (result.requestType == 0) {
                    //网络数据
                    if (isActive) {
                        Log.i("=============", "发送网络数据$result")
                        _shareFlow.value = result
                    }
                    if (result is Result.Success) {
                        Log.i("=============", "更新数据库数据")
                        Test2Repository.insert(result.data)
                    }
                } else {
                    if (isActive) {
                        Log.i("=============", "发送数据库数据$result")
                        _shareFlow.value = result
                    }
                }
            }, completion = { result ->
                Log.i("=============", "发送完成")
                _shareFlow.value = result
            }).launchIn(this)
        }
    }

    /**
     * 合并发送
     */
    fun sub() {
        viewModelScope.launch {
            val flow = flowOf(Test2Repository.getNetList())
            val flow1 = flowOf(Test2Repository.getPinkerData())
            flow.combine(flow1) { resultList, resultData ->
                var list = mutableListOf<PinkerConfig>()
                var data: PinkerData? = null
                if (resultList is Result.Success) {
                    list = resultList.data
                    if (resultData is Result.Success) {
                        data = resultData.data
                    }
                }
                Result.Success(Pinker(list, data))
            }.onStart {
                _sharedFlow2.emit(Result.Loading(isLoading = true))
            }.onCompletion {
                _sharedFlow2.emit(Result.Loading(isLoading = false))
            }.collect { value ->
                _sharedFlow2.emit(value)
            }
        }
    }
//
//    fun login(map: MutableMap<String, String>) {
//        viewModelScope.launch {
//            flow {
//                emit(Test2Repository.goLogin(map))
//            }.onStart {
//                _shareFlowLogin.emit(Result.Loading(isLoading = true))
//            }.onCompletion {
//                _shareFlowLogin.emit(Result.Loading(isLoading = false))
//            }.collect { result ->
//                _shareFlowLogin.emit(result)
//            }
//        }
//    }

    fun toLogin(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flow {
                emit(Test2Repository.loginAsync(map).await())
            }.catch { t: Throwable ->
                _shareFlowLogin.emit(Result.Error(t))
            }.onStart {
                _shareFlowLogin.emit(Result.Loading(isLoading = true))
            }.onCompletion {
                _shareFlowLogin.emit(Result.Loading(isLoading = false))
            }.collect { result ->
                _shareFlowLogin.emit(Result.Success(result.data!!))
            }
        }
    }
}