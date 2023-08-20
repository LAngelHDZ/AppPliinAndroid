package com.example.pliin.apppliin.data.network.dto.getGuideCod


import com.google.gson.annotations.SerializedName

data class GetGuideCodDto(
    @SerializedName("query")
    val queryGGC: List<QueryGGC?>?
)