package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class PortalDataInfoModel(
    @SerializedName("database")
    val database: String?,
    @SerializedName("table")
    val table: String?,
    @SerializedName("foundCount")
    val foundCount: Int?,
    @SerializedName("returnedCount")
    val returnedCount: Int?,

    )