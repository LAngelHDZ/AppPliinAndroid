package com.example.pliin.apppliin.domain.model.queryguideitem


import com.example.pliin.apppliin.data.model.queryguide.QueryGuidePliinModel
import com.example.pliin.apppliin.domain.model.messageitem.MessageItem
import com.example.pliin.apppliin.domain.model.messageitem.toDomain
import com.google.gson.annotations.SerializedName

data class QueryGuidePliinItem(
    @SerializedName("messages")
    val messages: List<MessageItem?>?,
    @SerializedName("response")
    val response: ResponseItem?
)

fun QueryGuidePliinModel.toDomain() =
    QueryGuidePliinItem(
        messages?.map { it!!.toDomain() },
        response?.toDomain()
    )