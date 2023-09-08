package com.example.pliin.apppliin.data.database.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.example.pliin.apppliin.data.database.entities.ManifestEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity

data class UserWithManifest(
    @ColumnInfo("direccion") val direccionesDircompleta: String?,
)
