package com.rain.rain.retrofit.moydleb

import androidx.room.Dao
import androidx.room.Query
import com.rain.rain.retrofit.lib.BaseDaoApi

@Dao
interface ModelBDaoApi : BaseDaoApi<ModelB> {

    @Query("SELECT * FROM b_model")
    fun query(): MutableList<ModelB>

    @Query("SELECT * FROM b_model WHERE uid IN (:userIds)")
    fun query(userIds: LongArray): MutableList<ModelB>

    @Query("SELECT * FROM b_model WHERE uid = (:userIds)")
    fun query(userIds: Long): ModelB

    @Query("DELETE FROM b_model ")
    fun delete()
}