package com.example.pliin.apppliin.data.model.manifests


import com.google.gson.annotations.SerializedName

data class DataManifest(
    @SerializedName("fieldData")
    val fieldData: FieldData,
    @SerializedName("modId")
    val modId: String,
    @SerializedName("portalData")
    val portalData: PortalData,
    @SerializedName("recordId")
    val recordId: String
)