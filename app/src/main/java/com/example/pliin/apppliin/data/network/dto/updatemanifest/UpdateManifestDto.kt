package com.example.pliin.apppliin.data.network.dto.updatemanifest

import com.google.gson.annotations.SerializedName

data class UpdateManifestDto(
    @SerializedName("fieldData")
    val fieldDataUM: FieldDataUM
)