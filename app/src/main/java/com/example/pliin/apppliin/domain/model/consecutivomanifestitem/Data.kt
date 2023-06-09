package com.example.pliin.apppliin.domain.model.consecutivomanifestitem


import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.DataConsecutivoM
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("fieldData")
    val fieldData: FieldData?,
    @SerializedName("portalData")
    val portalData: PortalData?,
    @SerializedName("recordId")
    val recordId: String?,
    @SerializedName("modId")
    val modId: String?
)

fun DataConsecutivoM.toDomain() =
    Data(
        fieldDataConsecutivoM?.toDomain(),
        portalDataCOnsecutivoM?.toDomain(),
        recordId,
        modId
    )