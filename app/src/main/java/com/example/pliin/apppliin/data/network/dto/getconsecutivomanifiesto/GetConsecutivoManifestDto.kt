package com.example.pliin.apppliin.data.network.dto.getconsecutivomanifiesto


import com.google.gson.annotations.SerializedName

data class GetConsecutivoManifestDto(
    @SerializedName("limit")
    val limit: String? = "1",
    @SerializedName("query")
    val query: List<Query?>?,
    @SerializedName("sort")
    val sort: List<Sort?>?
)