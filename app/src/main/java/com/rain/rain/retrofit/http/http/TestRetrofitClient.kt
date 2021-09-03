package com.rain.rain.retrofit.http.http

import com.rain.rain.retrofit.http.App
import com.rain.rain.retrofit.http.base.BaseRetrofitClient
import com.rain.rain.retrofit.http.base.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

/**
 *@Author: Rain
 *@Date: 2021/8/23 16:06
 *@Description: retrofitHttp
 */
object TestRetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(TestService::class.java, TestService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        val httpCacheDirectory = File(App.CONTEXT.cacheDir, "test")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                }else{
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
                response
            }
    }
}