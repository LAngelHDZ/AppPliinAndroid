package com.example.pliin.apppliin.data.model.deliverymodel

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("fieldData")
    val fieldData: FieldDataModel?,
    @SerializedName("portalData")
    val portalData: PortalDataModel?,
    @SerializedName("recordId")
    val recordId: String?,
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("portalDataInfo")
    val portalDataInfo: List<PortalDataInfoModel?>?,

    )