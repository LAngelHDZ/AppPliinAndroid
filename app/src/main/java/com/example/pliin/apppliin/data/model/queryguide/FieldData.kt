package com.example.pliin.apppliin.data.model.queryguide


import com.google.gson.annotations.SerializedName

data class FieldData(
    @SerializedName("guia")
    val guia: String?,
    @SerializedName("retorno")
    val retorno: String?
)