package com.example.pliin.apppliin.data.model.responseregisterdelivery

import com.example.pliin.apppliin.data.model.message.Message


import com.google.gson.annotations.SerializedName

data class ResponseRegisterDeliveryModel(
    @SerializedName("messages")
    val messages: List<Message?>?,
    @SerializedName("response")
    val response: Response?
)