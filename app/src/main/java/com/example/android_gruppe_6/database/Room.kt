package com.example.android_gruppe_6.database

import android.content.Context
import androidx.room.*

@Dao
interface HarborDao {

    // Get tide data and harbour info
    @Transaction
    @Query("SELECT * FROM DbHarbour WHERE name LIKE :harbor")
    fun getHarborWithTide(harbor: String): List<HarborWithTide>

    // Get tide info
    @Query("SELECT * FROM DbTide WHERE harbor LIKE :harbor")
    fun getTide(harbor: String): List<DbTide>

    // Get tide info on tides from spesific day
    @Query("SELECT * FROM DbTide WHERE harbor LIKE :harbor AND day LIKE :day")
    fun getTideOneDay(harbor: String, day: Int): List<DbTide>

    // Insert harbour
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTide(tide: List<DbTide>)

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