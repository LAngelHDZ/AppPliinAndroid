package com.example.pliin.apppliin.domain.model.queryguideitem


import com.example.pliin.apppliin.data.model.queryguide.Response
import com.example.pliin.apppliin.domain.model.datainfoitem.DataInfoItem
import com.example.pliin.apppliin.domain.model.datainfoitem.toDomain
import com.google.gson.annotations.SerializedName

data class ResponseItem(
    @SerializedName("data")
    val `data`: List<DataItem?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoItem?
)

fun Response.toDomain() =
    ResponseItem(
        data?.map { it!!.toDomain() },
        dataInfo?.toDomain()
    )