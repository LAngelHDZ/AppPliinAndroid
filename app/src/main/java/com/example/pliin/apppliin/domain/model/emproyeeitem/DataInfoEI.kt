package com.example.pliin.apppliin.domain.model.emproyeeitem


import com.example.pliin.apppliin.data.model.employeemodel.DataInfoEM
import com.google.gson.annotations.SerializedName

data class DataInfoEI(
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

fun DataInfoEM.toDomain() =
    DataInfoEI(
        database, foundCount, layout, returnedCount, table, totalRecordCount
    )