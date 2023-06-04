package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class GetDataGuideDRModel(
    @SerializedName("response")
    val response: ResponseModel?,
    @SerializedName("messages")
    val messages: List<MessageModel?>?
)