package com.example.pliin.apppliin.data.model.responseregisterdelivery


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)