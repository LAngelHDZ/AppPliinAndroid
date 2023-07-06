package com.example.pliin.apppliin.data.network.dto.direccionguide


import com.google.gson.annotations.SerializedName

data class FieldDataDG(
    @SerializedName("guia")
    val guia: String,
    @SerializedName("telefono")
    val telefono: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("dir1")
    val dir1: String,
    @SerializedName("dir2")
    val dir2: String,
    @SerializedName("dir3")
    val dir3: String,
    @SerializedName("cp")
    val cp: String,
    @SerializedName("municipio")
    val municipio: String
)