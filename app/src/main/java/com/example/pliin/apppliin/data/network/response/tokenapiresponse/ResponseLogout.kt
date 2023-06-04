package com.example.pliin.apppliin.data.network.response.tokenapiresponse

import com.google.gson.annotations.SerializedName

data class ResponseLogout(
    @SerializedName("token")
    val token: String?
)