package com.example.testkursvalute.data.repo.valutesList


import com.example.testkursvalute.api.ApiInterface
import com.example.testkursvalute.api.BaseRemoteDataSource
import com.example.testkursvalute.api.Result
import com.example.testkursvalute.api.model.ValutaInfo


import javax.inject.Inject


class ValutesListRemoteDataSource @Inject constructor(private val service: ApiInterface) :
    BaseRemoteDataSource() {

    suspend fun valutesList(): Result<ValutaInfo> =
        getResult {
            service.valutesList()
        }
}