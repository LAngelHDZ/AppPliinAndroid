package com.example.pliin.apppliin.domain.model.responselogoutitem


import com.example.pliin.apppliin.data.network.response.tokenapiresponse.MessageLogout
import com.google.gson.annotations.SerializedName

data class MessagesLogoutItem(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)

fun MessageLogout.toDomain() =
    MessagesLogoutItem(
        code,
        message
    )