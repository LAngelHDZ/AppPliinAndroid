package com.example.pliin.apppliin.domain.model.avisopagoitem



import com.example.pliin.apppliin.data.model.avisopago.AvisoModel
import com.example.pliin.apppliin.domain.model.messageitem.MessageItem
import com.example.pliin.apppliin.domain.model.messageitem.toDomain
import com.google.gson.annotations.SerializedName

data class AvisoItem(
    @SerializedName("messages")
    val messages: List<MessageItem?>?,
    @SerializedName("response")
    val response: ResponseAvisoItem?
)

fun AvisoModel.toDomain() = AvisoItem(
    messages?.map{it?.toDomain()},
    response?.toDomain()
)