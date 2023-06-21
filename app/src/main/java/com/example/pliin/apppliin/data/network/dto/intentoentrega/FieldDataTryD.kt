package com.example.pliin.apppliin.data.network.dto.intentoentrega


import com.google.gson.annotations.SerializedName

data class FieldDataTryD(
    @SerializedName("idGuia")
    val idGuia: String?,
    @SerializedName("observacion")
    val observacion: String?,
    @SerializedName("comentario")
    val comentario: String?
)