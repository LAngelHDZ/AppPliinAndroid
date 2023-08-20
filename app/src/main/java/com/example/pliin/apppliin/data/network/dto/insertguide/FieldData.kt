package com.example.pliin.apppliin.data.network.dto.insertguide


import com.google.gson.annotations.SerializedName

data class FieldData(
    @SerializedName("guia")
    val guia: String?,
    @SerializedName("pago")
    val pago: String?=""
)