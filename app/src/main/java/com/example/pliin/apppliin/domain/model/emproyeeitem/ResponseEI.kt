package com.example.pliin.apppliin.domain.model.emproyeeitem


import com.example.pliin.apppliin.data.model.employeemodel.ResponseEM
import com.google.gson.annotations.SerializedName

data class ResponseEI(
    @SerializedName("data")
    val `data`: List<DataEI?>?,
    @SerializedName("dataInfo")
    val dataInfo: DataInfoEI?
)

fun ResponseEM.toDomain() =
    ResponseEI(
        data!!.map { it!!.toDomain() },
        dataInfo!!.toDomain()
    )