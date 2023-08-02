package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pliin.apppliin.domain.model.SessionItem

@Entity(tableName = "session_table")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo (name = "username") val username: String?,
    @ColumnInfo (name = "password") val password: String?,
    @ColumnInfo (name = "token") val token: String?
)

fun SessionItem.toDatabase() = SessionEntity(username = username, password = password, token = token)
