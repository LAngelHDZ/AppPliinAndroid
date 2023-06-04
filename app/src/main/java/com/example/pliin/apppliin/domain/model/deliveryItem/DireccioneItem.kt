package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.DireccioneModel
import com.google.gson.annotations.SerializedName

data class DireccioneItem(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)

fun DireccioneModel.toDomain() =
    DireccioneItem(
        modId,
        recordId
    )