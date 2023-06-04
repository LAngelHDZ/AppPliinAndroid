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
    @SerializedName("a_materno")
    val aMaterno: String?,
    @SerializedName("a_paterno")
    val aPaterno: String?,
    @SerializedName("Area")
    val area: String?,
    @SerializedName("CP")
    val cP: String?,
    @SerializedName("CURP")
    val cURP: String?,
    @SerializedName("Calle")
    val calle: String?,
    @SerializedName("Ciudad")
    val ciudad: String?,
    @SerializedName("codigoEmpleado")
    val codigoEmpleado: String?,
    @SerializedName("Colonia")
    val colonia: String?,
    @SerializedName("Estado")
    val estado: String?,
    @SerializedName("fechaNacimiento")
    val fechaNacimiento: String?,
    @SerializedName("fotografia")
    val fotografia: String?,
    @SerializedName("municipio")
    val municipio: String?,
    @SerializedName("nombre")
    val nombre: String?,
    @SerializedName("numInt")
    val numInt: String?,
    @SerializedName("privilegios")
    val privilegios: String?,
    @SerializedName("RFC")
    val rFC: String?,
    @SerializedName("sexo")
    val sexo: String?,
    @SerializedName("StatusLaboral")
    val statusLaboral: String?,
    @SerializedName("SueldoComisionista")
    val sueldoComisionista: Int?,
    @SerializedName("SueldoOperativo")
    val sueldoOperativo: Int?,
    @SerializedName("username")
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
