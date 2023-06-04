package com.example.pliin.apppliin.data.model.employeemodel


import com.google.gson.annotations.SerializedName

data class DataEM(
    @SerializedName("fieldData")
    val fieldData: FieldDataEM?,
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("portalData")
    val portalData: PortalDataEM?,
    @SerializedName("recordId")
    val recordId: String?
)