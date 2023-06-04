package com.example.pliin.apppliin.domain.model.deliveryItem

import com.example.pliin.apppliin.data.model.deliverymodel.GetDataGuideDRModel
import com.google.gson.annotations.SerializedName

data class GetDataGuideRDItem(
    @SerializedName("messages")
    val messages: List<MessageItem?>?,
    @SerializedName("response")
    val response: ResponseItem?
)

fun GetDataGuideDRModel.toDomain() =
    GetDataGuideRDItem(
        messages?.map { it!!.toDomain() },
        response?.toDomain()
    )
