package com.example.pliin.apppliin.domain.model.responseregisterdeliveryi

import com.example.pliin.apppliin.data.model.responseregisterdelivery.Response
import com.example.pliin.apppliin.data.model.responseupdatestatus.ResponseUpdate

data class ResponseDI(
    val modId: String?,
    val recordId: String?
)

fun Response.toDomain() =
    ResponseDI(
        modId,
        recordId
    )

fun ResponseUpdate.toDomain() =
    ResponseDI(
        modId,
        recordId
    )