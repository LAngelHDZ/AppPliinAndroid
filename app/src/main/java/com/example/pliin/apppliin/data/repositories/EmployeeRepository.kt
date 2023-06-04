package com.example.pliin.apppliin.data.repositories

import android.annotation.SuppressLint
import com.example.pliin.apppliin.data.database.dao.EmployeeDao
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.data.network.services.EmployeeService
import com.example.pliin.apppliin.domain.model.emproyeeitem.EmployeeItem
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import com.example.pliin.apppliin.domain.model.emproyeeitem.toDomain
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val employeeService: EmployeeService,
    private val employeeDao: EmployeeDao
) {
    //Guarda la información del empleado en la DB
    @SuppressLint("SuspiciousIndentation")
    suspend fun saveDataEmployee(dataEmployee: EmployeeItem) {
        val data = dataEmployee.response?.data!![0]?.fieldData
        employeeDao.insertDataEmployee(data!!.toDatabase())
    }

    //verificaen la DB si existe la informaciòn del empleado
    suspend fun queryDataEmployee(user: String): Boolean {
        return employeeDao.queryDataEmployee(user)
    }

    //Solicita la informaciòn del empleado a la API
    suspend fun getEmployeeApi(user: String): EmployeeItem {
        val response = employeeService.getDataEmployee(user)
        return response.toDomain()
    }

    //Solicita la informaciòn del empleado a la DB
    suspend fun getEmployeeDB(user: String): FieldDataEI {
        val response = employeeDao.getDataEmployee(user)
        return response.toDomain()
    }

}