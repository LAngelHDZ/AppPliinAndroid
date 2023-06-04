package com.example.pliin.apppliin.data.network.dto.queryguidescanner


import com.google.gson.annotations.SerializedName


data class QueryGuideDto(
    @SerializedName("query")
    val query: List<query>
)