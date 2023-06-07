package com.example.pliin.apppliin.domain.model.consecutivomanifestitem


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)