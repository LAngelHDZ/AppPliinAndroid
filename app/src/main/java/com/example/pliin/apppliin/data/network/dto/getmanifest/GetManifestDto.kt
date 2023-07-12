package com.example.pliin.apppliin.data.network.dto.getmanifest


import com.google.gson.annotations.SerializedName

data class GetManifestDto(

    @SerializedName("query")
    val query: List<Querym>,
    @SerializedName("sort")
    val sort: List<Sortm>,
    @SerializedName("limit")
    val limit: String? = "1",
)