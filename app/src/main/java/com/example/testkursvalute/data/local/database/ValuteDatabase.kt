package com.example.testkursvalute.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ValuteEntity::class],
    version = 1,
    exportSchema = false)
abstract class ValuteDatabase: RoomDatabase() {

abstract fun valuteDao(): ValuteDao

companion object {
fun buildDatabase(context: Context): ValuteDatabase {
return Room.databaseBuilder(
    context,
    ValuteDatabase::class.java,
    "Valutes")
    .build()
}
}
}