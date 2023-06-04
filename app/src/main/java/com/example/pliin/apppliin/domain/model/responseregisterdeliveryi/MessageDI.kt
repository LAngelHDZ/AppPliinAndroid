package com.example.pliin.apppliin.domain.model.responseregisterdeliveryi

import  com.example.pliin.apppliin.data.model.responseregisterdelivery.Message
import com.example.pliin.apppliin.data.model.responseupdatestatus.MessageUpdate

data class MessageDI(
    val code: String?,
    val message: String?
)

fun Message.toDomain() =
    MessageDI(
        code,
        message
    )

fun MessageUpdate.toDomain() =
    MessageDI(
        code,
        message
    )