package com.example.pliin.apppliin.domain.model.emproyeeitem


import com.example.pliin.apppliin.data.model.employeemodel.DataEM
import com.google.gson.annotations.SerializedName

data class DataEI(
    @SerializedName("fieldData")
    val fieldData: FieldDataEI?,
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("portalData")
    val portalData: PortalDataEI?,
    @SerializedName("recordId")
    val recordId: String?
)

fun DataEM.toDomain() =
    DataEI(
        fieldData!!.toDomain(),
        modId,
        portalData!!.toDomain(),
        recordId
    )