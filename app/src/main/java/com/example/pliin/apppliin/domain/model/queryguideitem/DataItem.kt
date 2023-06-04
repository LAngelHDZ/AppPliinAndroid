package com.example.pliin.apppliin.domain.model.queryguideitem


import com.example.pliin.apppliin.data.model.queryguide.Data
import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("fieldData")
    val fieldData: FieldDataItem?,
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("portalData")
    val portalData: PortalDataItem?,
    @SerializedName("recordId")
    val recordId: String?
)

fun Data.toDomain() =
    DataItem(
        fieldData?.toDomain(),
        modId,
        portalData?.toDomain(),
        recordId
    )