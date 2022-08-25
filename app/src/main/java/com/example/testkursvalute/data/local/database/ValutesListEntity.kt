package com.example.testkursvalute.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "valutes_list")
data class ValutesListEntity(
    @PrimaryKey val id: String,
    val numCode: String?,
    val charCode: String?,
    val nominal: Int?,
    val name: String?,
    val value: Double?,
    val previous: Double?,
    val isFavorite: Boolean = false,
)