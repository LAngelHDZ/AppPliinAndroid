package com.example.pliin.apppliin.domain.model.emproyeeitem


import com.example.pliin.apppliin.data.model.employeemodel.EmployeeModel
import com.google.gson.annotations.SerializedName

data class EmployeeItem(
    @SerializedName("messages")
    val messages: List<MessageEI?>?,
    @SerializedName("response")
    val response: ResponseEI?
)

fun EmployeeModel.toDomain() =
    EmployeeItem(
        messages!!.map { it!!.toDomain() },
        response!!.toDomain()
    )