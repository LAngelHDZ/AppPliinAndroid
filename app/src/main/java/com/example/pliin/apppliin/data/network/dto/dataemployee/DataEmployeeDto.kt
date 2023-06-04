package com.example.pliin.apppliin.data.network.dto.dataemployee


import com.google.gson.annotations.SerializedName

data class DataEmployeeDto(
    @SerializedName("query")
    val query: List<Query?>?
)