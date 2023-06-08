package com.example.pliin.apppliin.domain.model.responseruditem


import com.example.pliin.apppliin.data.model.responserudmodel.ResponseModel
import com.google.gson.annotations.SerializedName

data class ResponseItem(
    @SerializedName("modId")
    val modId: String?,
    @SerializedName("recordId")
    val recordId: String?
)

fun ResponseModel.toDomain() =
    ResponseItem(
        modId, recordId
    )

