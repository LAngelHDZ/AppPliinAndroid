package com.example.pliin.apppliin.data.model.responseupdatestatus


import com.google.gson.annotations.SerializedName

data class ResponseUpdate(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)