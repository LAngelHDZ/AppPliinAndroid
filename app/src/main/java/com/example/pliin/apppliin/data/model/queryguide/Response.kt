package com.example.pliin.apppliin.data.model.queryguide


import com.example.pliin.apppliin.data.model.datainfo.DataInfoModel
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoModel?
)