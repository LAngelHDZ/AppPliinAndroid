package com.example.pliin.apppliin.data.network.response.clients

import com.example.pliin.apppliin.data.model.employeemodel.EmployeeModel
import com.example.pliin.apppliin.data.network.dto.dataemployee.DataEmployeeDto
import com.example.pliin.apppliin.data.network.dto.getallemployees.AllEmployeeDataDto
import com.example.pliin.apppliin.data.network.dto.registerdelivery.RegisterDeliveryDto
import com.example.pliin.apppliin.data.network.response.dataguideresponse.DataGuideResponse
import com.example.pliin.apppliin.data.network.response.datauserresponse.DataUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DataEmployeeClient {

    @POST("/fmi/data/v2/databases/PLIIN/layouts/EmpleadosAPI/_find/")
    suspend fun getDataEmployee(
        @Header("Authorization") bearer: String,
        @Body user: DataEmployeeDto
    ): Response<EmployeeModel>

    @POST("/fmi/data/v2/databases/PLIIN/layouts/EmpleadosAPI/_find/")
    suspend fun getAllEmployees(
        @Header("Authorization") bearer: String,
        @Body user: AllEmployeeDataDto
    ): Response<EmployeeModel>

}