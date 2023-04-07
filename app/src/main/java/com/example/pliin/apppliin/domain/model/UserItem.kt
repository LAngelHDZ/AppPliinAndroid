package com.example.pliin.apppliin.domain.model

import com.example.pliin.apppliin.data.database.entities.UserEntity
import com.example.pliin.apppliin.data.model.UserModel

//Clase de datos para crear el modelo user en la capa de dominio
data class UserItem(
    val user: String,
    val password: String,
    val idEmployee: String?,
    val employeeName: String?,
    val employeeType: String?,
    val employeeArea: String?,
    val employeeActive: Boolean?

)


//Funcion de mapeo de datos entre modelo User de la capa de  Data y el Modelo User de la capa de Dominio
fun UserModel.toDomain() =
    UserItem(
        user,
        password,
        idEmployee,
        employeeName,
        employeeType,
        employeeArea,
        employeeActive
    )
//Funcion de mapeo de datos entre el modelo user de la capa de dominio y la entidad User de la database
fun UserEntity.toDomain() =
    UserItem(
        user,
        password,
        idEmployee,
        employeeName,
        employeeType,
        employeeArea,
        employeeActive
    )