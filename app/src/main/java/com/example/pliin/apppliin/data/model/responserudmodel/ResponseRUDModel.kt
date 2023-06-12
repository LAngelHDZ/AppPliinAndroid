package com.example.pliin.apppliin.data.model.responserudmodel

import com.example.pliin.apppliin.data.model.message.Message


import com.google.gson.annotations.SerializedName

data class ResponseRUDModel(
    @SerializedName("response")
    val response: ResponseSetModel?,
    @SerializedName("messages")
    val messages: List<Message>?

)