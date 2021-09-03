package com.rain.rain.retrofit.http.test

import com.rain.rain.retrofit.http.base.BaseRetrofitClient
import okhttp3.OkHttpClient

/**
 *@Author: Rain
 *@Date: 2021/8/24 9:39
 *@Description:
 */
object KJSLRetrofitClient : BaseRetrofitClient() {

    override fun handleBuilder(builder: OkHttpClient.Builder) {
//        val httpCacheDirectory = File(App.CONTEXT.cacheDir, "xiaoNu")
//        val cacheSize = 10 * 1024 * 1024L // 10 MiB
//        val cache = Cache(httpCacheDirectory, cacheSize)
//        builder.cache(cache)
//        builder.addNetworkInterceptor(NetCacheInterceptor())
//        builder.addInterceptor(NotNetInterceptor())
        builder.addInterceptor(HeaderInterceptor())
    }
}