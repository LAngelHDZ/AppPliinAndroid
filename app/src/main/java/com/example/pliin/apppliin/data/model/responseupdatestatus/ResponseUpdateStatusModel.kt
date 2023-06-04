package com.example.pliin.apppliin.data.model.responseupdatestatus

import com.example.pliin.apppliin.data.model.message.Message
import com.google.gson.annotations.SerializedName

data class ResponseUpdateStatusModel(
    @SerializedName("messages")
    val messages: List<Message?>?,
    @SerializedName("response")
    val response: ResponseUpdate?
)