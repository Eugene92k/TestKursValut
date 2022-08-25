package com.example.testkursvalute.data.repo.valutesList

import com.example.testkursvalute.api.Result
import com.example.testkursvalute.api.model.Valute
import com.example.testkursvalute.api.successed
import com.example.testkursvalute.data.local.database.ValutesListEntity
import com.example.testkursvalute.data.local.preferences.PreferenceStorage
import com.example.testkursvalute.util.Constants
import java.util.*
import javax.inject.Inject

class ValutesListRepository @Inject constructor(
    private val valutesListRemoteDataSource: ValutesListRemoteDataSource,
    private val valutesListLocalDataSource: ValutesListLocalDataSource,
    private val preferenceStorage: PreferenceStorage
) {

    val allValutesList = valutesListLocalDataSource.allValutesList


    suspend fun valutesList() {

        when (val result = valutesListRemoteDataSource.valutesList()) {
            is Result.Success -> {
                if (result.successed) {

                    val favouriteCharCodes = valutesListLocalDataSource.favouriteIds()

                    val temporaryList = mutableListOf<Valute>()
                    temporaryList.addAll(result.data.valutes?.values ?: listOf())

                    if (temporaryList.size > 0) {

                        val customList = temporaryList.let {

                            it.filter { item -> item.id.isNullOrEmpty().not() }


                                .map { item ->
                                    ValutesListEntity(
                                        item.id ?: "",
                                        item.numCode,
                                        item.charCode,
                                        item.nominal,
                                        item.name,
                                        item.value,
                                        item.previous,
                                        favouriteCharCodes.contains(item.id)
                                    )
                                }
                        }


                        valutesListLocalDataSource.insertValutesIntoDatabase(customList)

                        preferenceStorage.timeLoadedAt = Date().time

                        Result.Success(true)
                    } else {
                        Result.Error(Constants.UNEXPECTED_ERROR)
                    }
                } else {

                    Result.Error(Constants.UNEXPECTED_ERROR)
                }
            }

            else -> {
                result as Result.Error
            }
        }
    }


    suspend fun updateFavouriteStatus(id: String): Result<ValutesListEntity> {
        val result = valutesListLocalDataSource.updateFavouriteStatus(id)
        return result?.let {
            Result.Success(it)
        } ?: Result.Error(Constants.UNEXPECTED_ERROR)
    }

    fun loadData(): Boolean {

        val lastLoadedTime = preferenceStorage.timeLoadedAt

        return Date().time - lastLoadedTime > 20 * 1000
    }
}