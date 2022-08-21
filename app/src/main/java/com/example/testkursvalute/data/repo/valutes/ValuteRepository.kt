package com.example.testkursvalute.data.repo.valutes

import com.example.testkursvalute.api.Result
import com.example.testkursvalute.api.model.Valute
import com.example.testkursvalute.api.successed
import com.example.testkursvalute.data.local.database.ValuteEntity
import com.example.testkursvalute.data.local.sharedpref.ShardPrefStorage
import java.util.*
import javax.inject.Inject

class ValuteRepository @Inject constructor(
    private val valutesRemoteDataSource: ValutesRemoteDataSource,
    private val valutesLocalDataSource: ValutesLocalDataSource,
    private val shardPrefStorage: ShardPrefStorage,
) {

    val allValutesList = valutesLocalDataSource.allValutes

    suspend fun valutesList() {
        when (val result = valutesRemoteDataSource.valutesList()) {
            is Result.Success -> {
                if (result.successed) {
                    val favouritesCharCodes = valutesLocalDataSource.favouriteIDs()

                    val temporaryList = mutableListOf<Valute>()
                    temporaryList.addAll(result.data.valutes?.values ?: listOf())

                    if (temporaryList.size > 0) {
                        val customList = temporaryList.let {
                            it.filter { item -> item.id.isNullOrEmpty().not() }

                                .map { item ->
                                    ValuteEntity(
                                        item.id ?: "",
                                        item.numCode,
                                        item.charCode,
                                        item.nominal,
                                        item.name,
                                        item.value,
                                        item.previous,
                                        favouritesCharCodes.contains(item.id)
                                    )
                                }
                        }

                        valutesLocalDataSource.insertValutesIntoDatabase(customList)

                        shardPrefStorage.timeLoadedAt = Date().time

                        Result.Success(true)
                    } else {
                        Result.Error("Ошибка")
                    }
                } else {
                    Result.Error("Ошибка")
                }
            }
            else -> {
                result as Result.Error
            }
        }
    }

    suspend fun updateFavouriteStatus(id: String): Result<ValuteEntity> {
        val result = valutesLocalDataSource.updateFavouriteStatus(id)
        return result?.let { Result.Success(it) } ?: Result.Error("Ошибка")
    }

    fun loadData(): Boolean {
        val lastLoadedTime = shardPrefStorage.timeLoadedAt

        return Date().time - lastLoadedTime > 20 * 1000
    }

}