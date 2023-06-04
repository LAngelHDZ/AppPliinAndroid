package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.MessageModel
import com.google.gson.annotations.SerializedName

data class MessageItem(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)

fun MessageModel.toDomain() =
    MessageItem(
        code,
        message
    )