package com.rain.rain.retrofit.http.http

import retrofit2.http.*

/**
 *@Author: Rain
 *@Date: 2021/8/23 16:01
 *@Description: retrofitHttp
 */
interface TestService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): TestResponse<ArticleList>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): TestResponse<User>

}