package com.example.pliin.apppliin.data.model.message


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)