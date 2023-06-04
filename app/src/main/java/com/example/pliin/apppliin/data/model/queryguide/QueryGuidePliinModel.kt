package com.example.pliin.apppliin.data.model.queryguide


import com.example.pliin.apppliin.data.model.message.Message
import com.google.gson.annotations.SerializedName

data class QueryGuidePliinModel(
    @SerializedName("messages")
    val messages: List<Message?>?,
    @SerializedName("response")
    val response: Response?
)