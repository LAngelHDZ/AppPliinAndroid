package com.example.pliin.apppliin.domain.model.responseregisterdeliveryi

import com.example.pliin.apppliin.data.model.responseregisterdelivery.ResponseRegisterDeliveryModel
import com.example.pliin.apppliin.data.model.responseupdatestatus.ResponseUpdateStatusModel
import com.example.pliin.apppliin.domain.model.messageitem.MessageItem
import com.example.pliin.apppliin.domain.model.messageitem.toDomain


data class ResponseRegisterDeliveryItem(
    val messages: List<MessageItem>?,
    val response: ResponseDI?
)

fun ResponseRegisterDeliveryModel.toDomain() =
    ResponseRegisterDeliveryItem(
        messages?.map { it!!.toDomain() },
        response?.toDomain()
    )

fun ResponseUpdateStatusModel.toDomain() =
    ResponseRegisterDeliveryItem(
        messages?.map { it!!.toDomain() },
        response?.toDomain()
    )
