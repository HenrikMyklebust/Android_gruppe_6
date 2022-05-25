package com.example.android_gruppe_6.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android_gruppe_6.domain.Harbors

@Dao
interface HarborDao {

    // Få info om havn (lat,lon) samt all data for havnen
    @Transaction
    @Query("SELECT * FROM DBHarbors WHERE name LIKE :harbor")
    fun getHarborWithData(harbor: String): List<HarborWithData>

    // Få kun data for havnen
    @Query("SELECT * FROM DBHarborData WHERE harbor LIKE :harbor")
    fun getData(harbor: String): List<DBHarborData>

    // Legg til data for havn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(d: List<DBHarborData>)

    // Legg til ny havn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHarbor(r: DBHarbors)
}

@Database(entities = [DBHarborData::class, DBHarbors::class],
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