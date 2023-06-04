package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.DataModel
import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("fieldData")
    val fieldData: FieldDataItem?,
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("portalData")
    val portalData: PortalDataItem?,
    @SerializedName("portalDataInfo")
    val portalDataInfo: List<PortalDataInfoItem?>?,
    @SerializedName("recordId")
    val recordId: String?
)

fun DataModel.toDomain() =
    DataItem(
        fieldData?.toDomain(),
        modId,
        portalData?.toDomain(),
        portalDataInfo?.map { it!!.toDomain() },
        recordId

    )