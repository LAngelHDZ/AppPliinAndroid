package com.example.pliin.apppliin.data.network.dto.createstatus


import com.google.gson.annotations.SerializedName

data class FieldDataCreateS(
    @SerializedName("IdGuia")
    val idGuia: String?,
    @SerializedName("estatus")
    val estatus: String?

)