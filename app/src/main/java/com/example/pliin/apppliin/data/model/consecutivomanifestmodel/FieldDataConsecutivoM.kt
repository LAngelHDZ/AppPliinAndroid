package com.example.pliin.apppliin.data.model.consecutivomanifestmodel


import com.google.gson.annotations.SerializedName

data class FieldDataConsecutivoM(
    @SerializedName("ConsecutivoFolio")
    val consecutivoFolio: Int?,
    @SerializedName("Fecha")
    val fecha: String?,
    @SerializedName("ClavePrincipal")
    val clavePrincipal: String?,
    @SerializedName("hora")
    val hora: String?
)