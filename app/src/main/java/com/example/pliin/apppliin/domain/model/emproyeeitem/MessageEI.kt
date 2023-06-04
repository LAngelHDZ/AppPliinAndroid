package com.example.pliin.apppliin.domain.model.emproyeeitem


import com.example.pliin.apppliin.data.model.employeemodel.MessageEM
import com.google.gson.annotations.SerializedName

data class MessageEI(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)

fun MessageEM.toDomain() =
    MessageEI(
        code,
        message
    )