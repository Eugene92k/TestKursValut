package com.example.testkursvalute.data.repo.valutesList

import androidx.lifecycle.LiveData
import com.example.testkursvalute.data.local.database.ValuteDatabase
import com.example.testkursvalute.data.local.database.ValutesListEntity

import javax.inject.Inject


class ValutesListLocalDataSource @Inject constructor(private val database: ValuteDatabase) {

    val allValutesList: LiveData<List<ValutesListEntity>> =
        database.valutesListDao().valutesListLiveData()


    suspend fun insertValutesIntoDatabase(valutesToInsert: List<ValutesListEntity>) {
        if (valutesToInsert.isNotEmpty()) {
            database.valutesListDao().insertValutesListEntity(valutesToInsert)
        }
    }

    suspend fun favouriteIds(): List<String> = database.valutesListDao().favouriteIds()

    suspend fun updateFavouriteStatus(id: String): ValutesListEntity? {
        val valute = database.valutesListDao().valuteById(id)
        valute?.let {
            val valutesListEntity = ValutesListEntity(
                it.id,
                it.numCode,
                it.charCode,
                it.nominal,
                it.name,
                it.value,
                it.previous,
                it.isFavorite.not()
            )

            if (database.valutesListDao().updateValutesListEntity(valutesListEntity) > 0) {
                return valutesListEntity
            }
        }

        return null
    }
}