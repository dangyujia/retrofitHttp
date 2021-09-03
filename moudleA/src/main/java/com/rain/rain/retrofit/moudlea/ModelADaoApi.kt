package com.rain.rain.retrofit.moudlea

import androidx.room.Dao
import androidx.room.Query
import com.rain.rain.retrofit.lib.BaseDaoApi

@Dao
interface ModelADaoApi : BaseDaoApi<ModelA> {

    @Query("SELECT * FROM a_model")
    suspend fun query(): MutableList<ModelA>

    @Query("SELECT * FROM a_model WHERE uid IN (:userIds)")
    fun query(userIds: LongArray): MutableList<ModelA>

    @Query("SELECT * FROM a_model WHERE uid = (:userIds)")
    fun query(userIds: Long): ModelA

    @Query("DELETE FROM a_model ")
    fun delete()
}

@Dao
interface ModelA2DaoApi : BaseDaoApi<PinkerConfig> {

    @Query("SELECT * FROM pinker_config")
    suspend fun query(): MutableList<PinkerConfig>

    @Query("DELETE FROM pinker_config ")
    suspend  fun delete()
}