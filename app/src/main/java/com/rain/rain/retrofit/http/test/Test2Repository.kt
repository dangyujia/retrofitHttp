package com.rain.rain.retrofit.http.test

import android.util.Log
import com.rain.rain.retrofit.http.base.BaseRepository
import com.rain.rain.retrofit.moudlea.ModelA2Dao
import com.rain.rain.retrofit.moudlea.PinkerConfig
import  com.rain.rain.retrofit.http.base.Result
import com.rain.rain.retrofit.http.requestBody

/**
 *@Author: Rain
 *@Date: 2021/8/23 16:20
 *@Description: https://github.com/wangxiongtao/kotlinBaseDemo/blob/master/app/src/main/java/com/dawn/kotlinbasedemo/vm/MainVm.kt
 * https://github.com/huyongli/AndroidKotlinCoroutine/blob/master/app/src/main/java/com/laohu/coroutines/model/repository/Repository.kt
 * https://blog.csdn.net/weixin_43901866/article/details/92072412?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0.control&spm=1001.2101.3001.4242
 *
 */
object Test2Repository : BaseRepository() {

    private val api =
        createService<KJSLRetrofitClient, LoginService>(KJSLRetrofitClient, LoginService.BASE_URL)

    suspend fun insert(list: MutableList<PinkerConfig>) = ModelA2Dao.insertAll(list)

    private suspend fun getRoomList() = ModelA2Dao.query()

    suspend fun getDBList(): Result<MutableList<PinkerConfig>> = dbResponse(getRoomList())

    private fun getListAsync() = api.getListAsync()

    suspend fun getNetList(): Result<MutableList<PinkerConfig>> = executeResponse(getListAsync()) {
        Log.i("====Repository=====", "修改前的数据：$it")
        Log.i("====Repository=====", "通过仓库修改数据 然后返回")
        val list = mutableListOf<PinkerConfig>()
        var index = 0
        it.forEach { pinker ->
            index++
            list.add(PinkerConfig(pinker.id, pinker.name + index, pinker.price))
        }
        it.clear()
        it.addAll(list)
        Log.i("====Repository=====", "修改后的数据：$it")
    }

    private fun basePinkerDataAsync() = api.basePinkerDataAsync()

    suspend fun getPinkerData(): Result<PinkerData> = executeResponse(basePinkerDataAsync())

    fun loginAsync(map: MutableMap<String, String>) = api.loginAsync(requestBody(map))

    suspend fun goLogin(map: MutableMap<String, String>) = executeApi(loginAsync(map))

}