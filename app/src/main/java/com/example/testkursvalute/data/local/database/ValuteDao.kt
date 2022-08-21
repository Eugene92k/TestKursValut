package com.example.testkursvalute.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ValuteDao {
@Query("SELECT * FROM valutes_list")
fun valutesListLiveData():LiveData<List<ValuteEntity>>

@Query("SELECT * FROM valutes_list WHERE id = :id")
suspend fun valuteById(id: String): ValuteEntity?

@Query("SELECT * FROM valutes_list WHERE id = :id")
suspend fun valuteLiveDataById(id: String): LiveData<ValuteEntity>

@Query("SELECT * FROM valutes_list WHERE isFavourite = 1")
fun favouriteValutes(): LiveData<List<ValuteEntity>>

@Query("SELECT * FROM valutes_list WHERE isFavourite = 1")
suspend fun favouriteIDs(): List<String>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertValutes(list: List<ValuteEntity>)

@Update
suspend fun updateValutes(data: ValuteEntity): Int

@Query("DELETE FROM valutes_list")
suspend fun deleteValute()
}
