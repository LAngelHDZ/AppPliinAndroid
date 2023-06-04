package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class DataInfoModel(
    @SerializedName("database")
    val database: String?,
    @SerializedName("layout")
    val layout: String?,
    @SerializedName("table")
    val table: String?,
    @SerializedName("totalRecordCount")
    val totalRecordCount: Int?,
    @SerializedName("foundCount")
    val foundCount: Int?,
    @SerializedName("returnedCount")
    val returnedCount: Int?,
)