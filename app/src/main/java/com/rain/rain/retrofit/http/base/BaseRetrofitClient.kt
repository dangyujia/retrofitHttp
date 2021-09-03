package com.rain.rain.retrofit.http.base

import com.rain.rain.retrofit.http.App
import com.rain.rain.retrofit.http.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 *@Author: Rain
 *@Date: 2021/8/23 11:59
 *@Description: retrofitHttp
 */
abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 15
    }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.apply {
                handleBuilder(this)
                if (BuildConfig.DEBUG && NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    addInterceptor(LoggingInterceptor())
                }
                connectTimeout(10, TimeUnit.SECONDS)
                readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            }
            return builder.build()
        }

    fun <T> getService(serviceClass: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(serviceClass)
    }
}