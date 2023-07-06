package com.example.pliin.apppliin.data.network.dto.datospqt


import com.google.gson.annotations.SerializedName

data class FieldDataDP(
    @SerializedName("guia")
    val guia: String,
    @SerializedName("Alto")
    val alto: Float,
    @SerializedName("Ancho")
    val ancho: Float,
    @SerializedName("Largo")
    val largo: Float,
    @SerializedName("PesoKg")
    val pesoKg: Float,
    @SerializedName("PesoVol")
    val pesoVol: Float,
    @SerializedName("Tipo")
    val tipo: String
)