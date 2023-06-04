package com.example.pliin.apppliin.data.model.datainfo


import com.google.gson.annotations.SerializedName

data class DataInfoModel(
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