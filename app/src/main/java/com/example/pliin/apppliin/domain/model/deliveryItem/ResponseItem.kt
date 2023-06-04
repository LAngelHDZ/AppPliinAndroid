package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.ResponseModel
import com.google.gson.annotations.SerializedName

data class ResponseItem(
    @SerializedName("data")
    val data: List<DataItem?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoItem?
)

fun ResponseModel.toDomain() =
    ResponseItem(
        data?.map { it!!.toDomain() },
        dataInfo?.toDomain()
    )