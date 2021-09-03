package com.rain.rain.retrofit.http.test

import android.util.Log
import com.rain.rain.retrofit.http.App
import com.rain.rain.retrofit.http.base.NetWorkUtils
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.IOException
import java.net.URLDecoder

/**
 *@Author: Rain
 *@Date: 2021/8/24 11:56
 *@Description: 网络拦截器
 */

//class CookiesInterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//
//        return chain.proceed(chain.request())
//    }
//}
/**
 * 网络缓存拦截器 ——> 目的：
 *                       在有网络的情况下对GET请求的接口进行缓存
 */
class NetCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        if (request.method == "GET") {//只有GET请求的类型才设置Cache
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            response = response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        return response
    }
}

/**
 * 无网拦截器  目的 ——>
 *                    在无网络的情况下对GET请求的接口返回缓存内容
 */
class NotNetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        //没有网络且是GET请求的接口调用缓存
        if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT) && request.method == "GET") {
            val maxAge = 60 * 60
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxAge")
                .build()
        }
        return chain.proceed(request)
    }
}

/**
 * 添加heard
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.apply {
            addHeader("apptype", "android")
            addHeader("version", "1.3.9")
            addHeader("vc", "39")
            addHeader("UDID", "Ai66e0vfWqWZ5YhxL3hzEJYawT-9UaOQPnRveoIZk9rI")
            addHeader("DeviceToken", "Ai66e0vfWqWZ5YhxL3hzEJYawT-9UaOQPnRveoIZk9rI")
            addHeader("Type", "1")
            addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJSUzI1NiJ9.eyJtZW1iZXJzIjoie1widGdDb2RlXCI6bnVsbCxcInJvbmdUb2tlblwiOm51bGwsXCJ1ZGlkXCI6bnVsbCxcInViaFwiOm51bGwsXCJ1cGhvbmVcIjpudWxsLFwidWZhY2VcIjpudWxsLFwiYXV0aG9yaXphdGlvblwiOm51bGwsXCJ1dGdDb2RlXCI6bnVsbCxcInVpZFwiOjEyODA0MDk0NjUzNTg2NDcyOTYsXCJ1bmlja1wiOm51bGx9IiwianRpIjoiTjJReU9EQTNZemt0TURRellTMDBZMlU0TFRrMVlUQXRZbVEzTXpkaE5XWmhaakExIiwiZXhwIjoxODQ2MDU0MjgwfQ.g5bV64R5CCBev4QhTF-mstouXx5856RRZw-WoTF4oN45yi7dC-jJtQGfyYwu2G5DGqkxIk2xqN7wJ2zahobolxScyfllATHLMp-vl0yXdeZX1BQSl2q0GHlFGEROBN_9M2-l7Lxq8GrnI9rUNRddzE40Nj3CW34mbUkltZyi52yctYBmNfB_T8mkhIoWfpTcwRGkfXQT-9QRAe_SoNxswcFRHiWG8JFmWGdXG_xMfCw0eIbRwfjvd6xOJ3rRrWhNFQuGYt16M2nDqTeIB6i8ErVD8czu2bSenpJ32BbOgQNQ0XdVYciY2QGk4aBA9YdTaZJ_WgGqv2r9rMYtELjklA"
            )
        }
        return chain.proceed(builder.build())
    }
}