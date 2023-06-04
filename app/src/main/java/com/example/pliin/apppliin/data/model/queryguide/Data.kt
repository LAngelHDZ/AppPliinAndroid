package com.example.pliin.apppliin.data.model.queryguide


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("fieldData")
    val fieldData: FieldData?,
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("portalData")
    val portalData: PortalData?,
    @SerializedName("recordId")
    val recordId: String?
)