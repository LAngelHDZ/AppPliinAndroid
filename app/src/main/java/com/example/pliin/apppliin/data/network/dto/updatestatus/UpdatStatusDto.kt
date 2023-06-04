package com.example.pliin.apppliin.data.network.dto.updatestatus


import com.google.gson.annotations.SerializedName

data class UpdatStatusDto(
    @SerializedName("fieldData")
    val fieldData: FieldData?
)