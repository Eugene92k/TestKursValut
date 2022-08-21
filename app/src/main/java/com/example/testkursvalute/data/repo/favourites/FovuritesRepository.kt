package com.example.testkursvalute.data.repo.favourites

import androidx.lifecycle.LiveData
import com.example.testkursvalute.data.local.database.ValuteEntity
import com.example.testkursvalute.api.Result
import javax.inject.Inject

class FavouritesRepository @Inject constructor(private val favouritesLocalDataSource: FavouritesLocalDataSource) {

    val favouriteValues: LiveData<List<ValuteEntity>> =favouritesLocalDataSource.favouriteValutes

    suspend fun updateFavouriteStatus(id: String): Result<ValuteEntity> {
val result = favouritesLocalDataSource.updateFavouriteStatus(id)
        return result?.let {
            Result.Success(it)
        }?:Result.Error(ERROR)
    }

    companion object {
        const val ERROR = "Ошибка"
    }

}