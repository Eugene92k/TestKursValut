package com.example.testkursvalute.data.repo.valutes

import com.example.testkursvalute.api.ApiInterface
import com.example.testkursvalute.api.BaseRemoteDataSource
import com.example.testkursvalute.api.Result
import com.example.testkursvalute.api.model.Valute
import com.example.testkursvalute.api.model.ValuteInfo
import javax.inject.Inject

class ValutesRemoteDataSource @Inject constructor(private val service: ApiInterface): BaseRemoteDataSource() {
    suspend fun valutesList(): Result<ValuteInfo> = getResult {
        service.valutesList()
    }
}