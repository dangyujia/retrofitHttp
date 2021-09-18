package com.rain.rain.retrofit.http

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import com.rain.rain.retrofit.http.base.Result

/**
 *@Author: Rain
 *@Date: 2021/8/24 10:05
 *@Description:
 */

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)

inline fun <reified T> Gson.fromJsonList(json: String): T {
    val token = object : TypeToken<T>() {}
    return fromJson(json, token.type)
}

object GsonUtils {
    private var gson: Gson? = null
    fun getInstance(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson as Gson
    }
}

//转Json
inline fun <reified T> toJson(value: T): String {
    val adapter = MoshiUtils.getInstance().adapter(T::class.java)
    return adapter.toJson(value)
}

//解析类
inline fun <reified T> String.fromJson(): T {
    val adapter = MoshiUtils.getInstance().adapter(T::class.java)
    return adapter.fromJson(this) ?: throw IllegalAccessException("解析异常.")
}

// 类相互转换 T to R
inline fun <reified T, reified R> transform(to: T): R = toJson(to).fromJson()

//moshi 解析器初始化
object MoshiUtils {
    private var moshi: Moshi? = null
    fun getInstance(): Moshi {
        if (moshi == null) {
            moshi = Moshi.Builder().build()
        }
        return moshi as Moshi
    }
}

inline fun <reified T> requestBody(value: T): RequestBody =
    toJson(value).toRequestBody("application/json; charset=utf-8".toMediaType())

val MAIN_HANDLER = Handler(Looper.getMainLooper())

fun postDelay(block: () -> Unit, delay: Long) {
    MAIN_HANDLER.postDelayed({ block() }, delay)
}

fun post(block: () -> Unit) {
    postDelay(block, 0)
}

//解析网络返回
inline fun <reified T : Any> Result<T>.request(
    success: (T) -> Unit,
    loading: (Boolean) -> Unit,
    error: (Throwable) -> Unit
) {
    when (this) {
        is Result.Success -> success(data)
        is Result.Loading -> loading(isLoading)
        is Result.Error -> error(exception)
    }
}