package com.rain.rain.retrofit.http.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import com.rain.rain.retrofit.http.test.KResult

/**
 *@Author: Rain
 *@Date: 2021/8/23 16:14
 *@Description: retrofitHttp Transaction
 */
open class BaseRepository {

    /**
     * 构建 Service
     * @param value retrofit对象  必须继承BaseRetrofitClient
     * @param url  自定义BaseUrl 根据实际项目开发 可以不传 默认时本地获取
     */
    protected inline fun <reified T : BaseRetrofitClient, reified R> createService(
        value: T,
        url: String
    ): R {
        return value.getService(R::class.java, url)
    }

    /**
     * 网络请求封装返回
     */
    suspend fun <T : Any> executeResponse(
        response: Deferred<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): Result<T> {
        return coroutineScope {
            try {
                val t = response.await()
                successBlock?.let { it() }
                Result.Success(t)
            } catch (e: Exception) {
                errorBlock?.let { it() }
                Result.Error(IOException(e))
            }
        }
    }

    /**
     * 数据库数据请求返回
     */
    suspend fun <T : Any> executeResponse(
        response: T,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): Result<T> {
        return coroutineScope {
            val success = Result.Success(response)
            success.requestType = 1
            successBlock?.let { it() }
            success
        }
    }

    /**
     * 网络请求封装返回
     */
    suspend fun <T : Any> executeApi(
        response: Deferred<KResult<T>>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): Result<T> {
        return coroutineScope {
            try {
                val t = response.await()
                successBlock?.let { it() }
                Result.Success(t.data!!)
            } catch (e: Exception) {
                errorBlock?.let { it() }
                Result.Error(IOException(e))
            }
        }
    }
}