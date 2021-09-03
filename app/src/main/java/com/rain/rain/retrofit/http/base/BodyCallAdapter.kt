package com.rain.rain.retrofit.http.base

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.Type

/**
 *@Author: Rain
 *@Date: 2021/8/26 9:58
 *@Description: 如果是自定义返回bean
 */
class BodyCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Deferred<T>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Deferred<T> {
        val deferred = CompletableDeferred<T>()
        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null){
                        deferred.complete(body)
                    }else{
                        deferred.completeExceptionally(HttpException(response))
                    }
                } else {
                    deferred.completeExceptionally(HttpException(response))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                deferred.completeExceptionally(t)
            }
        })
        return deferred
    }
}