package com.example.pliin.apppliin.data.network.dto.getGuideCod


import com.google.gson.annotations.SerializedName

data class QueryGGC(
    @SerializedName("guia")
    val guia: String?
)