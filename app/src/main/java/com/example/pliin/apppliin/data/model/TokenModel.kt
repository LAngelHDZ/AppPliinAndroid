package com.example.pliin.apppliin.data.model


import com.google.gson.annotations.SerializedName


data class TokenModel(
    @SerializedName("token") val token: String?,
    // @SerializedName("code") val code: String,
    // @SerializedName("message") val message: String,
)
