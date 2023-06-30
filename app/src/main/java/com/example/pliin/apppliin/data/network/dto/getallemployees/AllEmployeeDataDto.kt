package com.example.pliin.apppliin.data.network.dto.getallemployees


import com.google.gson.annotations.SerializedName

data class AllEmployeeDataDto(
    @SerializedName("query")
    val query: List<Query>
)