package com.example.testkursvalute.data.repo.favourites

import androidx.lifecycle.LiveData
import com.example.testkursvalute.api.Result
import com.example.testkursvalute.data.local.database.ValutesListEntity
import com.example.testkursvalute.util.Constants
import javax.inject.Inject


class FavouritesRepository @Inject constructor(private val favouritesLocalDataSource: FavouritesLocalDataSource) {

    val favouriteValutes: LiveData<List<ValutesListEntity>> =
        favouritesLocalDataSource.favouriteValutes

    suspend fun updateFavouriteStatus(id: String): Result<ValutesListEntity> {
        val result = favouritesLocalDataSource.updateFavouriteStatus(id)
        return result?.let {
            Result.Success(it)
        } ?: Result.Error(Constants.UNEXPECTED_ERROR)
    }
}