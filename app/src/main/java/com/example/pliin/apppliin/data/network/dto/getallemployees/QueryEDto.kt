package com.example.pliin.apppliin.data.network.dto.getallemployees


import com.google.gson.annotations.SerializedName

data class QueryEDto(
    @SerializedName("Area")
    val area: String = "Operador Logistico",
    @SerializedName("statusLaboral")
    val statusLaboral: String = "Activo"
)