package com.example.pliin.apppliin.data.model.responserudmodel


import com.google.gson.annotations.SerializedName

data class ResponseSetModel(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)