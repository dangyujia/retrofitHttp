package com.rain.rain.retrofit.http.base

import android.util.Log
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.IOException
import java.net.URLDecoder

/**
 * 日志拦截
 */
class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        requestLog(request) //打印请求日志
        val response = chain.proceed(request)
        return responseLog(response)
    }

    /**
     * 响应日志打印
     */
    private fun responseLog(response: Response): Response {
        Log.e("响应日志开始打印", "**********响应日志开始打印*********")
        val builder = response.newBuilder()
        val clone = builder.build()
        Log.d("响应地址", clone.request.url.toString())
        val headers = clone.request.headers
        headers.forEach { pair ->
            Log.d("响应heard", pair.first + "->" + pair.second)
        }
        Log.d("响应code", "${clone.code}")
        Log.d("响应message", clone.message)
        var body = clone.body
        body?.let {
            it.contentType()?.apply {
                if (mediaType(this)) {
                    var resp = ""
                    try {
                        resp = it.string()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    Log.d("响应内容", resp)
                    Log.e("响应日志结束打印", "**********响应日志结束打印*********")
                    body = resp.toResponseBody(this)
                    return response.newBuilder().body(body).build()
                } else {
                    Log.d("响应内容", "发生错误-非文本类型")
                    Log.e("响应日志结束打印", "**********响应日志结束打印*********")
                }
            }
        }
        return response
    }

    /**
     * 请求日志打印
     */
    private fun requestLog(request: Request) {
        Log.e("请求日志开始打印", "**********请求日志开始打印*********")
        val url = request.url.toString()
        Log.d("请求地址", url)
        Log.d("请求方式", request.method)
        val body = request.body
        body?.apply {
            contentType()?.apply {
                Log.d("请求内容类型", this.toString())
                if (mediaType(this)) {
                    Log.d("请求内容", bodyToString(request))
                } else {
                    Log.d("请求内容", "无法识别")
                }
            }
        }
        Log.e("请求日志结束打印", "**********请求日志结束打印*********")
    }

    /**
     * 请求类型
     */
    private fun mediaType(mediaType: MediaType): Boolean {
        if (mediaType.type == "text") {
            return true
        }
        return when (mediaType.subtype) {
            "json", "xml", "html", "webviewhtml", "x-www-form-urlencoded" -> true
            else -> false
        }
    }

    /**
     * 请求参数
     */
    private fun bodyToString(request: Request): String {
        val build = request.newBuilder().build()
        val params: String
        val buffer = Buffer()
        try {
            build.body?.writeTo(buffer)
            val read = buffer.readUtf8()
            params = URLDecoder.decode(read, "UTF-8")
        } catch (e: IOException) {
            e.printStackTrace()
            return "在解析请求内容时候发生了异常-非字符串"
        }
        return params
    }
}