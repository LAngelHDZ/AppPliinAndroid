package com.example.pliin.apppliin.data.network.dto.updatepago


import com.google.gson.annotations.SerializedName

data class UpdatePagoDto(
    @SerializedName("fieldData")
    val fieldDataPago: FieldDataPago?
)