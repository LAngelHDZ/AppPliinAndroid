package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pliin.apppliin.domain.model.GuideItem

@Entity(tableName = "guide_table")
data class GuideEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int=0,
    @ColumnInfo(name = "idGuia") val idGuia: String?
)

//fun GuideItem.toDatabase() = GuideEntity( idGuia = idGuia)