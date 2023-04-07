package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pliin.apppliin.domain.model.UserItem

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "id_employee") val idEmployee: String?,
    @ColumnInfo(name = "employee_name") val employeeName: String?,
    @ColumnInfo(name = "employee_type") val employeeType: String?,
    @ColumnInfo(name = "employee_area") val employeeArea: String?,
    @ColumnInfo(name = "employee_active") val employeeActive: Boolean?,
    //@ColumnInfo(name = "work_area") val workArea:String

)

fun UserItem.toDatabase() = UserEntity(
    user = user,
    password = password,
    idEmployee = idEmployee,
    employeeName = employeeName,
    employeeType = employeeType,
    employeeActive = employeeActive,
    employeeArea = employeeArea
)