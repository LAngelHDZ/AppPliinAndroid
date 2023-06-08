package com.example.pliin.apppliin.domain.model.responseruditem


import com.example.pliin.apppliin.data.model.responserudmodel.ResponseRUDModel
import com.example.pliin.apppliin.domain.model.messageitem.MessageItem
import com.example.pliin.apppliin.domain.model.messageitem.toDomain


import com.google.gson.annotations.SerializedName

data class ResponseRUDItem(
    @SerializedName("messages")
    val messages: List<MessageItem>?,
    @SerializedName("response")
    val response: ResponseItem?
)

fun ResponseRUDModel.toDomain() =
    ResponseRUDItem(
        messages?.map { it.toDomain() },
        response?.toDomain()
    )
