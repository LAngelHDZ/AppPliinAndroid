package com.example.pliin.apppliin.data.network.dto.getmanifest


import com.google.gson.annotations.SerializedName

data class Sortm(
    @SerializedName("fieldName")
    val fieldName: String,
    @SerializedName("sortOrder")
    val sortOrder: String = "descend"
)