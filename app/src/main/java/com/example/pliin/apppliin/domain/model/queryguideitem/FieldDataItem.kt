package com.example.pliin.apppliin.domain.model.queryguideitem


import com.example.pliin.apppliin.data.model.queryguide.FieldData
import com.google.gson.annotations.SerializedName

data class FieldDataItem(
    @SerializedName("guia")
    val guia: String?,
    @SerializedName("retorno")
    val retorno: String?
)

fun FieldData.toDomain() =
    FieldDataItem(
        guia,
        retorno
    )