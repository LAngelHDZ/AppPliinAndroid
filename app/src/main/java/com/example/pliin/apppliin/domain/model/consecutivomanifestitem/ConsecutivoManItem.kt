package com.example.pliin.apppliin.domain.model.consecutivomanifestitem


import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.ConsecutivoManModel
import com.google.gson.annotations.SerializedName

import com.example.pliin.apppliin.data.model.message.Message
import com.example.pliin.apppliin.domain.model.messageitem.MessageItem
import com.example.pliin.apppliin.domain.model.messageitem.toDomain

data class ConsecutivoManItem(
    @SerializedName("response")
    val response: Response?,
    @SerializedName("messages")
    val messages: List<MessageItem>?
)

fun ConsecutivoManModel.toDomain() =
    ConsecutivoManItem(
        response?.toDomain(),
        messages?.map { it.toDomain() }
    )