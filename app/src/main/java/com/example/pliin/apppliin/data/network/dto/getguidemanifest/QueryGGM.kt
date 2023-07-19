package com.example.pliin.apppliin.data.network.dto.getguidemanifest


import com.google.gson.annotations.SerializedName

data class QueryGGM(
    @SerializedName("IdPreM")
    val idPreM: String?,
    @SerializedName("Observacion")
    val observacion: String? ="Asignado"
)