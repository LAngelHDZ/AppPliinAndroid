package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)