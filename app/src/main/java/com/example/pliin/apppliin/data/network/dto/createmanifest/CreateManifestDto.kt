package com.example.pliin.apppliin.data.network.dto.createmanifest

import com.google.gson.annotations.SerializedName

data class CreateManifestDto(
    @SerializedName("fieldData")
    val fieldDataUM: FieldData
)