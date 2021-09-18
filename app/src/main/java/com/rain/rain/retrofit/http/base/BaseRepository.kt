package com.rain.rain.retrofit.http.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import com.rain.rain.retrofit.http.test.KResult
import retrofit2.HttpException

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
     *      block 返回数据操作
     */
    protected suspend inline fun <reified T : Any> executeResponse(
        response: Deferred<T>,
        noinline block: ((T) -> Unit)? = null,
    ): Result<T> {
        return try {
            val await = response.await()
            block?.apply { invoke(await) }
            Result.Success(await)
        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        in 200..300 -> {

                        }
                        in 400..500 -> {
                            
                        }
                        in 500..600 -> {

                        }
                    }
                }
                else -> {
                    Result.Error(e)
                }
            }
            Result.Error(e)
        }
    }

    /**
     * 数据库数据请求返回
     */
    protected inline fun <reified T : Any> dbResponse(
        response: T,
        noinline block: ((T) -> Unit)? = null
    ): Result<T> {
        val success = Result.Success(response)
        success.requestType = 1
        block?.apply { invoke(response) }
        return success
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