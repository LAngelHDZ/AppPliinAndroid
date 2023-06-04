package com.example.pliin.apppliin.data.model.employeemodel


import com.google.gson.annotations.SerializedName

data class MessageEM(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)