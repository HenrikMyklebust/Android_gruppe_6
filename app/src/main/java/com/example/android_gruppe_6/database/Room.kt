package com.example.android_gruppe_6.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HarborDao {
    @Transaction
    @Query("SELECT * FROM DatabaseHarbor")
    fun getHarborAndData(): LiveData<List<HarborAndData>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(harborData: List<DatabaseHarborData>)

}

@Database(entities = [DatabaseHarborData::class, DatabaseHarbor::class],
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