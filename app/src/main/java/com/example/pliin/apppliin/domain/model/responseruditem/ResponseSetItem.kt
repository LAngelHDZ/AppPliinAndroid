package com.example.pliin.apppliin.domain.model.responseruditem


import com.example.pliin.apppliin.data.model.responserudmodel.ResponseSetModel
import com.google.gson.annotations.SerializedName

data class ResponseSetItem(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)

fun ResponseSetModel.toDomain() =
    ResponseSetItem(
        modId, recordId
    )

