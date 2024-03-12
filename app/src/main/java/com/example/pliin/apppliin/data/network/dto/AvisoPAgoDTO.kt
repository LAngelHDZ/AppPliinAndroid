package com.example.pliin.apppliin.data.network.dto


import com.google.gson.annotations.SerializedName

data class AvisoPAgoDTO(
    @SerializedName("query")
    val queryAvisoPago: List<QueryAvisoPago?>?
)