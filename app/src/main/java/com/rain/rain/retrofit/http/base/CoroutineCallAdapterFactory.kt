package com.rain.rain.retrofit.http.base

import kotlinx.coroutines.Deferred
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *@Author: Rain
 *@Date: 2021/8/26 9:53
 *@Description: 协程retrofit adapter 工厂
 */
class CoroutineCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>"
            )
        }
        val responseType = getParameterUpperBound(0, returnType)

        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == Response::class.java) {
            if (responseType !is ParameterizedType) {
                throw IllegalStateException(
                    "Response must be parameterized as Response<Foo> or Response<out Foo>"
                )
            }
            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }
}