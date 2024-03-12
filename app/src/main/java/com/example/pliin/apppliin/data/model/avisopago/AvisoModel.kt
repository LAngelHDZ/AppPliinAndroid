package com.example.pliin.apppliin.data.model.avisopago


import com.example.pliin.apppliin.data.model.message.Message
import com.google.gson.annotations.SerializedName

data class AvisoModel(
    @SerializedName("messages")
    val messages: List<Message?>?,
    @SerializedName("response")
    val response: ResponseAvisoModel?
)