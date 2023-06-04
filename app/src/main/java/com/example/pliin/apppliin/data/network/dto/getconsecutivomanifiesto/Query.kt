package com.example.pliin.apppliin.data.network.dto.getconsecutivomanifiesto


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("Fecha")
    val fecha: String?
)