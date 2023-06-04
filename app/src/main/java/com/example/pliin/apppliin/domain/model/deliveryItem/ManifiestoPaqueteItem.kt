package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.ManifiestoPaqueteModel
import com.google.gson.annotations.SerializedName

data class ManifiestoPaqueteItem(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)

fun ManifiestoPaqueteModel.toDomain() =
    ManifiestoPaqueteItem(
        modId,
        recordId
    )