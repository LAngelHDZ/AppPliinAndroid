package com.example.pliin.apppliin.data.network.dto.insertguide


import com.google.gson.annotations.SerializedName

data class InsertGuideDto(
    @SerializedName("fieldData")
    val fieldData: FieldData?
)