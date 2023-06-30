package com.example.pliin.apppliin.data.network.services

import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.model.employeemodel.*
import com.example.pliin.apppliin.data.network.dto.dataemployee.*
import com.example.pliin.apppliin.data.network.dto.getallemployees.AllEmployeeDataDto
import com.example.pliin.apppliin.data.network.response.clients.DataEmployeeClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmployeeService @Inject constructor(
    private val employeeclient: DataEmployeeClient,
    private val daotoken: TokenDao
) {
    private var username: String = ""
    suspend fun getDataEmployee(user: String): EmployeeModel {
        username = user
        val bearer = daotoken.getToken()?.token
        val query = DataEmployeeDto(listOf(Query(user)))

        return withContext(Dispatchers.IO) {
            val response = employeeclient.getDataEmployee("Bearer $bearer", query)
            val data = if (response.isSuccessful) {
                response.body()!!
            } else {
                when (response.code()) {
                    500 -> {
                        EmployeeModel(
                            listOf(MessageEM("401", "No records match the request")),
                            refillResponse(),
                        )
                    }
                    401 -> {
                        EmployeeModel(
                            listOf(MessageEM("401", "No records match the request")),
                            refillResponse(),
                        )
                    }
                    else -> {
                        EmployeeModel(
                            listOf(MessageEM("401", "No records match the request")),
                            refillResponse(),
                        )
                    }
                }
            }
            data
        }
    }

    suspend fun getAllEmployee(): EmployeeModel {
        val bearer = daotoken.getToken().token
        val query = AllEmployeeDataDto(listOf())

        return withContext(Dispatchers.IO) {
            val response = employeeclient.getAllEmployees("Bearer $bearer", query)
            val data = if (response.isSuccessful) {
                response.body()!!
            } else {
                when (response.code()) {
                    500 -> {
                        EmployeeModel(
                            listOf(MessageEM("401", "No records match the request")),
                            refillResponse(),
                        )
                    }

                    401 -> {
                        EmployeeModel(
                            listOf(MessageEM("401", "No records match the request")),
                            refillResponse(),
                        )
                    }

                    else -> {
                        EmployeeModel(
                            listOf(MessageEM("401", "No records match the request")),
                            refillResponse(),
                        )
                    }
                }
            }
            data
        }
    }


    private fun refillResponse(): ResponseEM {
        return ResponseEM(
            listOf(
                DataEM(
                    FieldDataEM(
                        "",
                        "",
                        "DEVELOPER" +
                                "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "Admin",
                        "",
                        "Developer",
                        "",
                        "",
                        "Activo",
                        0,
                        0,
                        username
                    ),
                    "",
                    PortalDataEM(),
                    ""
                )
            ), DataInfoEM("", 0, "", 0, "", 0)
        )
    }
}