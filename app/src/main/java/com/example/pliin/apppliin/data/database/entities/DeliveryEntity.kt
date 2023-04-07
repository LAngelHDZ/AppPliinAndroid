package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Delivery_table")
data class DeliveryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int=0,
    @ColumnInfo(name = "guide") val guide:String,
    @ColumnInfo(name = "creator") val creator:String,
    @ColumnInfo(name = "nameOperator") val nameOperator:String,
    @ColumnInfo(name = "station") val station:String?,
    @ColumnInfo(name = "route") val route:String,
    @ColumnInfo(name = "code") val code:String,
    @ColumnInfo(name = "nameClient") val nameClient:String,
    @ColumnInfo(name = "kg") val kg:Float?,
    @ColumnInfo(name = "direction") val direction:String,
    @ColumnInfo(name = "parentClient") val parentClient:String?,
    @ColumnInfo(name = "hour", ) val hour:String?,
    @ColumnInfo(name = "date") val date:Date?,
    @ColumnInfo(name = "nameWhoReceives") val nameWhoRecives:String?,
    @ColumnInfo(name = "payStatus") val payStatus:String,
    @ColumnInfo(name = "valueGuide") val valueGuide:Float,
    @ColumnInfo(name = "week") val week:String


) {

}