package com.example.pliin.apppliin.data.model


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class UserModel(
    @SerializedName("username") val user: String,
    @SerializedName("password") val password: String,
    /* @field:Json(name = "username") val user: String,
     @field:Json(name = "codigoEmpleado") val idEmployee: String?,
     @field:Json(name = "password") val password:String,
     @field:Json(name = "privilegios") val employeeType: String?,
     @field:Json(name = "nombreCompleto") val employeeName: String?,
     @field:Json(name = "Area") val employeeArea:String?,
     @field:Json(name = "StatusLaboral") val employeeActive:String?*/
)








