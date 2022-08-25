package com.example.testkursvalute.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ValuteListDao {

    @Query("SELECT * FROM valutes_list")
    fun valutesListLiveData(): LiveData<List<ValutesListEntity>>


    @Query("SELECT * FROM valutes_list WHERE id = :id")
    suspend fun valuteById(id: String): ValutesListEntity?


    @Query("SELECT * FROM valutes_list WHERE id = :id")
    fun valuteLiveDataById(id: String): LiveData<ValutesListEntity>


    @Query("SELECT * FROM valutes_list WHERE isFavorite = 1")
    fun favouriteValutes(): LiveData<List<ValutesListEntity>>


    @Query("SELECT id FROM valutes_list WHERE isFavorite = 1")
    suspend fun favouriteIds(): List<String>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValutesListEntity(list: List<ValutesListEntity>)


    @Update
    suspend fun updateValutesListEntity(data: ValutesListEntity): Int


    @Query("DELETE FROM valutes_list")
    suspend fun deleteAll()
}