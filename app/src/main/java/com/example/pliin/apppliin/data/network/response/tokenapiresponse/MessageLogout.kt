package com.example.pliin.apppliin.data.network.response.tokenapiresponse

import com.google.gson.annotations.SerializedName

data class MessageLogout(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)