package com.example.pliin.apppliin.data.network.dto.registerdelivery


import com.google.gson.annotations.SerializedName

data class RegisterDeliveryDto(
    @SerializedName("fieldData")
    val fieldData: FieldData?
)