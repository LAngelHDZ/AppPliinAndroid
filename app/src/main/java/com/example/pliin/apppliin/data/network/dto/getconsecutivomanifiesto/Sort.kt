package com.example.pliin.apppliin.data.network.dto.getconsecutivomanifiesto


import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("fieldName")
    val fieldName: String?,
    @SerializedName("sortOrder")
    val sortOrder: String = "descend"
)