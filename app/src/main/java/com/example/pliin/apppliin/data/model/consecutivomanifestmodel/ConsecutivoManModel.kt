package com.example.pliin.apppliin.data.model.consecutivomanifestmodel


import com.google.gson.annotations.SerializedName

import com.example.pliin.apppliin.data.model.message.Message

data class ConsecutivoManModel(
    @SerializedName("response")
    val response: ResponseConsecutive?,
    @SerializedName("messages")
    val messages: List<Message>?
)