package com.rain.rain.retrofit.moudlea

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *@Author: Rain
 *@Date: 2021/8/25 10:51
 *@Description: retrofitHttp
 */
@Entity(tableName = "a_model")
class ModelA {

    @PrimaryKey(autoGenerate = true)
    var uid = 0

    @ColumnInfo(name = "nick_name")
    var nickName: String? = null

    @ColumnInfo(name = "create_time")
    var createTime: String? = null

    override fun toString(): String {
        return "ModelA(uid=$uid, nickName=$nickName, createTime=$createTime)"
    }


}

@Entity(tableName = "pinker_config")
data class PinkerConfig(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: String
)