package com.example.testkursvalute.data.local.database

import android.content.Context
import androidx.room.*


@Database(entities = [ValutesListEntity::class], version = 1, exportSchema = false)
abstract class ValuteDatabase : RoomDatabase() {
    abstract fun valutesListDao(): ValuteListDao

    companion object {

        fun buildDatabase(context: Context): ValuteDatabase {
            return Room.databaseBuilder(context, ValuteDatabase::class.java, "Valutes").build()
        }
    }
}


