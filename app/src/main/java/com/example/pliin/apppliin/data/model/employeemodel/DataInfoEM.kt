package com.example.pliin.apppliin.data.model.employeemodel


import com.google.gson.annotations.SerializedName

data class DataInfoEM(
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