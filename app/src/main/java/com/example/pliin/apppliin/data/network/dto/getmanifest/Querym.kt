package com.example.pliin.apppliin.data.network.dto.getmanifest


import com.google.gson.annotations.SerializedName

data class Querym(
    @SerializedName("Fecha")
    val fecha: String,
    @SerializedName("NombreOperador")
    val nombreOperador: String
)