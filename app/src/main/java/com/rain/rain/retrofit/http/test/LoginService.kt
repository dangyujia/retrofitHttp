package com.rain.rain.retrofit.http.test

import com.rain.rain.retrofit.moudlea.PinkerConfig
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *@Author: Rain
 *@Date: 2021/8/24 9:16
 *@Description: retrofitHttp
 */
interface LoginService {

    companion object {
        const val BASE_URL = "http://192.168.3.69:80"
    }

    @POST("auth/sysMember/toLogin")
    fun loginAsync(@Body body: RequestBody): Deferred<KResult<UserData>>

    @GET("pinker/pinker/pinker-config/list")
    fun getListAsync(): Deferred<MutableList<PinkerConfig>>

    @GET("pinker/goods/pinker/goods/data/pinker")
    fun basePinkerDataAsync(): Deferred<PinkerData>

}