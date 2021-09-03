package com.rain.rain.retrofit.http.base

/**
 *@Author: Rain
 *@Date: 2021/8/31 11:29
 *@Description: 网络请求返回实体类
 */
sealed class Result<out T : Any> {
    var requestType: Int = 0 // 默认为 0 网络请求数据  1 数据库数据
    data class Success<out T : Any>(val data: T) : Result<T>()  // 成功
    data class Error(val exception: Throwable) : Result<Nothing>() //失败
    data class Loading(val isLoading: Boolean = true) : Result<Nothing>() //加载中  true  弹框  false 关闭

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading[isLoading=$isLoading]"
            else -> ""
        }
    }
}