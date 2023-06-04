package com.example.pliin.apppliin.data.network.dto.createstatus


import com.google.gson.annotations.SerializedName

data class CreateStatusGuideDto(
    @SerializedName("fieldData")
    val fieldData: FieldDataCreateS?
)