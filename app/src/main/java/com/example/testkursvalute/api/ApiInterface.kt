package com.example.testkursvalute.api


import com.example.testkursvalute.api.model.ValutaInfo
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("daily_json.js")
    suspend fun valutesList(): Response<ValutaInfo>
}