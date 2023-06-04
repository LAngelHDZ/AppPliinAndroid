package com.example.pliin.apppliin.data.network.dto.queyguidereception


import com.google.gson.annotations.SerializedName

data class QueryGuidePliinDto(
    @SerializedName("query")
    val query: List<Query?>?
)