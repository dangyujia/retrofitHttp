package com.rain.rain.retrofit.http.base

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 *@Author: Rain
 *@Date: 2021/8/26 10:07
 *@Description: 如果是Response 基类返回则走这个
 */
class ResponseCallAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, Deferred<Response<T>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Deferred<Response<T>> {
        val deferred = CompletableDeferred<Response<T>>()
        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                deferred.completeExceptionally(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                deferred.complete(response)
            }
        })
        return deferred
    }
}