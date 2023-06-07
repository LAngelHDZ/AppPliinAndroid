package com.example.pliin.apppliin.data.model.consecutivomanifestmodel


import com.google.gson.annotations.SerializedName

data class DataConsecutivoM(
    @SerializedName("fieldData")
    val fieldDataConsecutivoM: FieldDataConsecutivoM?,
    @SerializedName("portalData")
    val portalDataCOnsecutivoM: PortalDataConsecutivoM?,
    @SerializedName("recordId")
    val recordId: String?,
    @SerializedName("modId")
    val modId: String?
)