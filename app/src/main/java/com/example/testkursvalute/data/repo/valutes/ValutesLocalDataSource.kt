package com.example.testkursvalute.data.repo.valutes

import androidx.lifecycle.LiveData
import com.example.testkursvalute.data.local.database.ValuteDatabase
import com.example.testkursvalute.data.local.database.ValuteEntity
import javax.inject.Inject

class ValutesLocalDataSource @Inject constructor(private val database: ValuteDatabase) {

    val allValutes: LiveData<List<ValuteEntity>> = database.valuteDao().valutesListLiveData()

    suspend fun insertValutesIntoDatabase(valutesToInsert: List<ValuteEntity>) {
        if (valutesToInsert.isNotEmpty()) {
            database.valuteDao().insertValutesListEntity(valutesToInsert)
        }
    }

    suspend fun favouriteIDs(): List<String> = database.valuteDao().favouriteIds()

    suspend fun updateFavouriteStatus(id: String): ValuteEntity? {
        val valute = database.valuteDao().valuteById(id)
        valute?.let {
            val valuteEntity = ValuteEntity(
                it.id,
                it.numCode,
                it.charCode,
                it.nominal,
                it.name,
                it.value,
                it.previous,
                it.isFavourite.not()
            )

            if (database.valuteDao().updateValutesListEntity(valuteEntity) > 0) {
                return valuteEntity
            }
        }
        return null
    }
}