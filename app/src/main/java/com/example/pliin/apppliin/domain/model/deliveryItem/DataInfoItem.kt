package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.DataInfoModel
import com.google.gson.annotations.SerializedName

data class DataInfoItem(
    @SerializedName("database")
    val database: String?,
    @SerializedName("foundCount")
    val foundCount: Int?,
    @SerializedName("layout")
    val layout: String?,
    @SerializedName("returnedCount")
    val returnedCount: Int?,
    @SerializedName("table")
    val table: String?,
    @SerializedName("totalRecordCount")
    val totalRecordCount: Int?
)

fun DataInfoModel.toDomain() =
    DataInfoItem(
        database, foundCount, layout, returnedCount, table, totalRecordCount
    )