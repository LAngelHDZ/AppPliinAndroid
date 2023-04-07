package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "manifest_table")
data class ManifestEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo("manifestKey") val manifestKey:String,
    @ColumnInfo("fkemployye") val fkEmpleye:String,
    @ColumnInfo("company") val company:String?,
    @ColumnInfo("createDate") val createDate:Date?,
    @ColumnInfo("route") val route:String?,
    @ColumnInfo("routeStatus") val routeStatus:String?,
    @ColumnInfo("manifestStatus") val manifestStatus:String?,
    @ColumnInfo("totalPackages") val totalPackages:Int?,
    @ColumnInfo("totalGuides") val totalGuides:Int?
) {
}