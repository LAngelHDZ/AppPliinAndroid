package com.example.pliin.apppliin.domain.model.messageitem


import com.example.pliin.apppliin.data.model.message.Message
import com.google.gson.annotations.SerializedName

data class MessageItem(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)

fun Message.toDomain() =
    MessageItem(
        code,
        message

    )