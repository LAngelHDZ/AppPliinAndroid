package com.example.pliin.apppliin.domain.model.responselogoutitem


import com.example.pliin.apppliin.data.network.response.tokenapiresponse.ResponseLogoutModel
import com.google.gson.annotations.SerializedName

data class ResponseLogoutItem(
    @SerializedName("messages")
    val messagesLogout: List<MessagesLogoutItem?>?,
    @SerializedName("response")
    val responseLogut: LogoutItem?
)

fun ResponseLogoutModel.toDomain() =
    ResponseLogoutItem(
        messages?.map { it!!.toDomain() },
        response!!.toDomain()
    )