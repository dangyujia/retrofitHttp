package com.rain.rain.retrofit.moydleb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@Author: Rain
 *@Date: 2021/8/25 10:51
 *@Description: retrofitHttp
 */
@Entity(tableName = "b_model")
class ModelB {
    
    @PrimaryKey(autoGenerate = true)
    var uid = 0

    @ColumnInfo(name = "nick_name")
    var nickName: String? = null

    @ColumnInfo(name = "create_time")
    var createTime: String? = null

    override fun toString(): String {
        return "ModelB(uid=$uid, nickName=$nickName, createTime=$createTime)"
    }
}