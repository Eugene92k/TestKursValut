package com.example.testkursvalute.data.repo.favourites

import androidx.lifecycle.LiveData
import com.example.testkursvalute.data.local.database.ValuteDatabase
import com.example.testkursvalute.data.local.database.ValuteEntity
import javax.inject.Inject

class FavouritesLocalDataSource @Inject constructor(private val database: ValuteDatabase) {

    val favouriteValutes: LiveData<List<ValuteEntity>> = database.valuteDao().favouriteValutes()

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