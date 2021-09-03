package com.rain.rain.retrofit.http.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

fun <T> CoroutineScope.request(dsl: RequestDsl<T>.() -> Unit) {
    //在主线程中开启协程
    this.launch(Dispatchers.Main) {
        val coroutine = RequestDsl<T>().apply(dsl)
        coroutine.api?.let { deferred ->
            //当协程取消的时候，取消网络请求
            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    coroutine.clean()
                }
            }
            //await 等待异步执行的结果
            try {
                val response = deferred.await()
                coroutine.onSuccess?.invoke(response)
            } catch (e: ConnectException) {
                coroutine.onFail?.invoke(e.message ?: "请求失败", -1)
            } catch (e: IOException) {
                coroutine.onFail?.invoke("未知网络错误", -1)
            } catch (e: HttpException) {
                val msg: String = "业务逻辑报错"
//                when (e.code()) {
//                    NetworkErrorCodeEnums.RESPONSE_CODE_FAIL.value -> {
//                        msg = "接口404"
//                    }
//                    NetworkErrorCodeEnums.RESPONSE_CODE_BUSINESS_FAIL.value -> {
//                        msg = e.response()?.message() ?: "业务逻辑报错"
//                    }
//                    NetworkErrorCodeEnums.RESPONSE_CODE_RE_LOGIN.value -> {
//                        msg = "登录身份已过期，请重新登录"
//                        BaseManager.instance.Logout()
//                    }
//                    else -> {
//                        msg = "业务逻辑报错"
//                    }
//                }
                coroutine.onFail?.invoke(msg, e.code())
            }
            coroutine.onComplete?.invoke()
        }
    }
}