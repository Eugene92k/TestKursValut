package com.example.testkursvalute.api.model

import com.google.gson.annotations.SerializedName

data class ValuteInfo(
    @SerializedName("Valute")
    var valutes: LinkedHashMap<String, Valute>?
)
