package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.PortalDataInfoModel
import com.google.gson.annotations.SerializedName

data class PortalDataInfoItem(
    @SerializedName("database")
    val database: String?,
    @SerializedName("foundCount")
    val foundCount: Int?,
    @SerializedName("returnedCount")
    val returnedCount: Int?,
    @SerializedName("table")
    val table: String?
)

fun PortalDataInfoModel.toDomain() =
    PortalDataInfoItem(
        database, foundCount, returnedCount, table
    )