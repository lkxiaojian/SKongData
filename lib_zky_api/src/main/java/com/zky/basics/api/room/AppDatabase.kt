package com.zky.basics.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zky.basics.api.room.Dao.AccountLevelDao

import com.zky.basics.api.splash.entity.AccountLevel
import java.io.File

/**
 * Created by lk
 * Date 2019-12-25
 * Time 15:45
 * Detail:
 *             //数据库使用 用例
//          var  testRoomDbDao =
//                AppDatabase.getDatabase(getApplication())?.testRoomDbDao()!!
//            val testRoomDb = TestRoomDb(2231, "name", 3, "1", "3")
//            val list= arrayListOf<TestRoomDb>()
//            list.add(testRoomDb)
//            testRoomDbDao.insertOrUpdate(list)
//            val users = testRoomDbDao.users()
 */
@Database(entities = [AccountLevel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountLevelDao(): AccountLevelDao
    companion object {
        private var INSTANCE //创建单例
                : AppDatabase? = null
        fun getDatabase(context: Context?): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        var path = (
                                "${File.separator}mnt${File.separator}sdcard${File.separator}database${File.separator}${context?.packageName}${File.separator}")
                        val file = File(path)
                        if(!file.exists()){
                            file.mkdirs()
                        }
                        path += "skData.db"
                        INSTANCE = Room.databaseBuilder(context!!, AppDatabase::class.java, path)
                            .addCallback(sOnOpenCallback)
//                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
//                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }

//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE 'test' ADD COLUMN 'pub_year1' INTEGER NOT NULL DEFAULT 0")
//
//            }
//        }
//
//        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                try {
//                    database.execSQL("CREATE TABLE 'Fruit' ('id' INTEGER, 'name' TEXT NOT NULL DEFAULT '', " +
//                            "PRIMARY KEY(`id`))")
//                    database.execSQL("ALTER TABLE 'test' ADD COLUMN 'pub_year2' INTEGER NOT NULL DEFAULT 0")
//                }catch (e:Exception){
//                    Log.e("","")
//
//                }
//
//            }
//        }
        private val sOnOpenCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }
    }
}