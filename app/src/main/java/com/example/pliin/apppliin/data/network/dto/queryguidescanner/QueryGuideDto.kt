package com.example.pliin.apppliin.data.network.dto.queryguidescanner
import com.google.gson.annotations.SerializedName


data class QueryGuideDto(
    @SerializedName("query")
    val query: List<query>,
    @SerializedName("sort")
    val sort: List<Sort?>?,
    @SerializedName("limit")
    val limit: String? = "1"
)