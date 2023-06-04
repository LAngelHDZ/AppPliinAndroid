package com.example.pliin.apppliin.data.model.deliverymodel


import com.google.gson.annotations.SerializedName

data class DireccioneModel(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)