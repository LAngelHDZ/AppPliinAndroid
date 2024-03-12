package com.example.pliin.apppliin.data.model.avisopago


import com.google.gson.annotations.SerializedName

data class DataAvisoModel(
    @SerializedName("fieldData")
    val fieldData: FieldDataAvisoModel?,
    @SerializedName("modId")
    val modId: String?, // 208
    @SerializedName("portalData")
    val portalData: PortalDataAvisoModel?,
    @SerializedName("recordId")
    val recordId: String? // 1
)