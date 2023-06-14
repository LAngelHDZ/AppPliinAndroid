package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pliin.apppliin.data.model.TokenModel
import com.example.pliin.apppliin.domain.model.GuideItem
import com.example.pliin.apppliin.domain.model.TokenItem


@Entity(tableName = "token_table")
data class TokenEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "token") val token: String? = ""
)

fun TokenItem.toDatabase() = TokenEntity(token = token)
fun TokenModel.toDatabase() = TokenEntity(token = token)

