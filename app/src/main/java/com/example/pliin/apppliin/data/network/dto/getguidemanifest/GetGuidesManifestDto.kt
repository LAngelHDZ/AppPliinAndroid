package com.example.pliin.apppliin.data.network.dto.getguidemanifest


import com.google.gson.annotations.SerializedName

data class GetGuidesManifestDto(
    @SerializedName("query")
    val queryGGM: List<QueryGGM?>?,
)