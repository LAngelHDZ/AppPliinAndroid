package com.example.pliin.apppliin.data.network.response.tokenapiresponse

import com.google.gson.annotations.SerializedName

data class ResponseLogoutModel(
    @SerializedName("messages")
    val messages: List<MessageLogout?>?,
    @SerializedName("response")
    val response: ResponseLogout?
)