package com.example.pliin.apppliin.data.network.dto.queryguidescanner


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class query(
    @SerializedName("IdPreM")
    val IdPreM: String? ="",
    @SerializedName("IdGuia")
    val IdGuia: String? ="",
    @SerializedName("Observacion")
    val Observacion: String =""
)