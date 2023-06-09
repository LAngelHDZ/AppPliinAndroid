package com.example.pliin.apppliin.domain.model.consecutivomanifestitem


import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.FieldDataConsecutivoM
import com.google.gson.annotations.SerializedName

data class FieldData(
    @SerializedName("ConsecutivoFolio")
    val consecutivoFolio: Int?,
    @SerializedName("Fecha")
    val fecha: String?,
    @SerializedName("ClavePrincipal")
    val clavePrincipal: String?,
    @SerializedName("hora")
    val hora: String?
)

fun FieldDataConsecutivoM.toDomain() =
    FieldData(
        consecutivoFolio, fecha, clavePrincipal, hora
    )
