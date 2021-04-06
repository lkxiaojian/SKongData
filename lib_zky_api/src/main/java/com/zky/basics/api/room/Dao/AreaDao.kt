package com.zky.basics.api.room.Dao

import androidx.room.*
import com.zky.basics.api.room.bean.Area


/**
 * Created by lk
 * Date 2020/9/24
 * Time 17:13
 * Detail:
 */
@Dao
interface AreaDao {

    @Query("SELECT * FROM Area")
    suspend fun all(): List<Area>

    @Query("SELECT * FROM Area WHERE code IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<Area?>?

    @Query("SELECT * FROM Area WHERE code = (:userIds)")
    fun loadAllByKey(userIds: Int): List<Area?>?

    @Query(
        "SELECT * FROM Area WHERE name LIKE :first AND " +
                "name LIKE :last LIMIT 1"
    )
    suspend fun findByName(first: String?, last: String?): Area?

    @Query("SELECT count(*) FROM Area")
    suspend fun count(): Int?


    suspend fun insertOrUpdate(vararg Areas: Area?) {
        deleteNoSuspend(*Areas)
        insertNoSuspend(*Areas)
    }


    @Insert
    suspend fun insertNoSuspend(vararg Areas: Area?)


    @Delete
    suspend fun delete(Area: Area?)

    @Delete
    suspend fun deleteNoSuspend(vararg Areas: Area?)


    @Query("delete from Area where code = :userID")
    suspend fun deleteById(userID: String)

    @Update
    suspend fun updateUsers(vararg Areas: Area?): Int

    @Query("select * from Area")
    suspend fun Areas(): List<Area?>?

}