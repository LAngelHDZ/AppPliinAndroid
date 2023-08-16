package com.example.pliin.apppliin.data.network.dto.createguidecod

import com.google.gson.annotations.SerializedName

data class FieldDataCod(
    @SerializedName("guia") val guia: String?="",
    @SerializedName("valor_guia") val valueCod: Float?=0f,
)