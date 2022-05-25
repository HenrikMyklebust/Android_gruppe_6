package com.example.android_gruppe_6.database

import android.content.Context
import androidx.room.*

@Dao
interface HarborDao {

    // Get tide data and harbour info
    @Transaction
    @Query("SELECT * FROM DbHarbour WHERE name LIKE :harbor")
    fun getHarborWithData(harbor: String): List<HarborWithData>

    // Get harbour info
    @Query("SELECT * FROM DbTide WHERE harbor LIKE :harbor")
    fun getData(harbor: String): List<DbTide>

    // Insert harbour
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(tide: List<DbTide>)

    // Insert tide
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHarbor(harbour: List<DbHarbour>)
}

@Database(entities = [DbTide::class, DbHarbour::class],
    version = 1)

abstract class HarborsDatabase : RoomDatabase() {
    abstract val harborDao: HarborDao
}

private lateinit var INSTANCE: HarborsDatabase

fun getDatabase(context: Context): HarborsDatabase {
    synchronized(HarborsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                HarborsDatabase::class.java,
                "harbors"
            ).build()
        }
    }
    return INSTANCE
}