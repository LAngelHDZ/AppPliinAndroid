package com.example.pliin.apppliin.domain.model.consecutivomanifestitem


import com.example.pliin.apppliin.data.model.consecutivomanifestmodel.ResponseConsecutive
import com.example.pliin.apppliin.domain.model.datainfoitem.DataInfoItem
import com.example.pliin.apppliin.domain.model.datainfoitem.toDomain
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("dataInfo")
    val dataInfo: DataInfoItem?,
    @SerializedName("data")
    val `data`: List<Data?>?
)

fun ResponseConsecutive.toDomain() =
    Response(
        dataInfo?.toDomain(),
        data?.map { it!!.toDomain() }
    )