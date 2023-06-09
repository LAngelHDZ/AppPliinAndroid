package com.example.pliin.apppliin.data.model.consecutivomanifestmodel


import com.example.pliin.apppliin.data.model.datainfo.DataInfoModel
import com.google.gson.annotations.SerializedName

data class ResponseConsecutive(
    @SerializedName("dataInfo")
    val dataInfo: DataInfoModel?,
    @SerializedName("data")
    val `data`: List<DataConsecutivoM?>?
)