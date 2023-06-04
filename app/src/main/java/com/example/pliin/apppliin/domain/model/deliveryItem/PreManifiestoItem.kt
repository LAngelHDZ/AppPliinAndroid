package com.example.pliin.apppliin.domain.model.deliveryItem


import com.example.pliin.apppliin.data.model.deliverymodel.PreManifiestoModel
import com.google.gson.annotations.SerializedName

data class PreManifiestoItem(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)

fun PreManifiestoModel.toDomain() =
    PreManifiestoItem(
        modId, recordId
    )
