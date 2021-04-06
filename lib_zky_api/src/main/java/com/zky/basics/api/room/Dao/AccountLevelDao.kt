package com.zky.basics.api.room.Dao

import androidx.room.*
import com.zky.basics.api.splash.entity.AccountLevel


/**
 * Created by lk
 * Date 2020/9/24
 * Time 17:13
 * Detail:
 */
@Dao
interface AccountLevelDao {

    @Query("SELECT * FROM AccountLevel")
    suspend fun all(): List<AccountLevel>

    @Query("SELECT * FROM AccountLevel WHERE attr_code IN (:codes)")
    fun loadAllByIds(codes: IntArray?): List<AccountLevel?>?

    @Query("SELECT * FROM AccountLevel WHERE attr_code = (:code)")
    fun loadAllByKey(code: String): List<AccountLevel?>?

//    @Query(
//        "SELECT * FROM AccountLevel WHERE name LIKE :first AND " +
//                "name LIKE :last LIMIT 1"
//    )
//    suspend fun findByName(first: String?, last: String?): AccountLevel?

    @Query("SELECT count(*) FROM AccountLevel")
    suspend fun count(): Int?


    suspend fun insertOrUpdate(vararg Areas: AccountLevel?) {
        deleteNoSuspend(*Areas)
        insertNoSuspend(*Areas)
    }


    @Insert
    suspend fun insertNoSuspend(vararg Areas: AccountLevel?)


    @Delete
    suspend fun delete(Area: AccountLevel?)

    @Delete
    suspend fun deleteNoSuspend(vararg Areas: AccountLevel?)


    @Query("delete from AccountLevel where attr_code = :code")
    suspend fun deleteById(code: String)

    @Update
    suspend fun updateUsers(vararg Areas: AccountLevel?): Int

    @Query("select * from AccountLevel")
    suspend fun AccountLevels(): List<AccountLevel?>?

}