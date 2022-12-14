package com.example.testkursvalute.data.repo.favourites

import androidx.lifecycle.LiveData
import com.example.testkursvalute.data.local.database.ValuteDatabase
import com.example.testkursvalute.data.local.database.ValutesListEntity
import javax.inject.Inject


class FavouritesLocalDataSource @Inject constructor(private val database: ValuteDatabase) {

    val favouriteValutes: LiveData<List<ValutesListEntity>> =
        database.valutesListDao().favouriteValutes()


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