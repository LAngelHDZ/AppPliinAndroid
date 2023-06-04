package com.example.pliin.apppliin.data.model.employeemodel


import com.google.gson.annotations.SerializedName

data class EmployeeModel(
    @SerializedName("messages")
    val messages: List<MessageEM?>?,
    @SerializedName("response")
    val response: ResponseEM?
)