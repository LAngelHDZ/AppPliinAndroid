package com.example.pliin.apppliin.data.network.dto.addguidemanifest


import com.google.gson.annotations.SerializedName

data class FieldDataAGM(
    @SerializedName("IdPreM")
    val idPreM: String,
    @SerializedName("IdGuia")
    val idGuia: String,
    @SerializedName("NumPaquetes")
    val numPaquetes: String,
    @SerializedName("Observacion")
    val observacion: String
)