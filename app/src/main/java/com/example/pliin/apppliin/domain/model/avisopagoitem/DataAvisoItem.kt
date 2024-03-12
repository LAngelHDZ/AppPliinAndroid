package com.example.pliin.apppliin.domain.model.avisopagoitem


import com.example.pliin.apppliin.data.model.avisopago.DataAvisoModel
import com.google.gson.annotations.SerializedName

data class DataAvisoItem(
    @SerializedName("fieldData")
    val fieldData: FieldDataAvisoItem?,
    @SerializedName("modId")
    val modId: String?, // 208
    @SerializedName("portalData")
    val portalData: PortalDataAvisoItem?,
    @SerializedName("recordId")
    val recordId: String? // 1
)

fun DataAvisoModel.toDomain() = DataAvisoItem(
    fieldData?.toDomain(),
    modId,
    portalData?.toDomain(),
    recordId
)