package com.example.pliin.apppliin.domain.model.responselogoutitem


import com.example.pliin.apppliin.data.network.response.tokenapiresponse.ResponseLogout
import com.google.gson.annotations.SerializedName

data class LogoutItem(
    @SerializedName("token")
    val token: String?
)

fun ResponseLogout.toDomain() =
    LogoutItem(
        token
    )