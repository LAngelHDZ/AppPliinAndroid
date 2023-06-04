package com.example.pliin.apppliin.data.model.responseupdatestatus


import com.google.gson.annotations.SerializedName

data class MessageUpdate(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)