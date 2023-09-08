package com.example.pliin.apppliin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Employee_table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo("a_materno")
    val aMaterno: String?,
    @ColumnInfo("a_paterno")
    val aPaterno: String?,
    @ColumnInfo("Area")
    val area: String?,
    @ColumnInfo("CP")
    val cP: String?,
    @ColumnInfo("CURP")
    val cURP: String?,
    @ColumnInfo("Calle")
    val calle: String?,
    @ColumnInfo("Ciudad")
    val ciudad: String?,
    @ColumnInfo("codigoEmpleado")
    val codigoEmpleado: String?,
    @ColumnInfo("Colonia")
    val colonia: String?,
    @ColumnInfo("Estado")
    val estado: String?,
    @ColumnInfo("fechaNacimiento")
    val fechaNacimiento: String?,
    @ColumnInfo("fotografia")
    val fotografia: String?,
    @ColumnInfo("municipio")
    val municipio: String?,
    @ColumnInfo("nombre")
    val nombre: String?,
    @ColumnInfo("numInt")
    val numInt: String?,
    @ColumnInfo("privilegios")
    val privilegios: String?,
    @ColumnInfo("RFC")
    val rFC: String?,
    @ColumnInfo("sexo")
    val sexo: String?,
    @ColumnInfo("StatusLaboral")
    val statusLaboral: String?,
    @ColumnInfo("SueldoComisionista")
    val sueldoComisionista: Int?,
    @ColumnInfo("SueldoOperativo")
    val sueldoOperativo: Int?,
    @ColumnInfo("username")
    val username: String?
)

fun FieldDataEI.toDatabase() =
    EmployeeEntity(
        aMaterno = aMaterno,
        aPaterno = aPaterno,
        area = area,
        cP = cP,
        cURP = cURP,
        calle = calle,
        ciudad = ciudad,
        codigoEmpleado = codigoEmpleado,
        colonia = colonia,
        estado = estado,
        fechaNacimiento = fechaNacimiento,
        fotografia = fotografia,
        municipio = municipio,
        nombre = nombre,
        numInt = numInt,
        privilegios = privilegios,
        rFC = rFC,
        sexo = sexo,
        statusLaboral = statusLaboral,
        sueldoComisionista = sueldoComisionista,
        sueldoOperativo = sueldoOperativo,
        username = username

    )
