package com.example.pliin.apppliin.domain.model.emproyeeitem


import com.example.pliin.apppliin.data.database.entities.EmployeeEntity
import com.example.pliin.apppliin.data.model.employeemodel.FieldDataEM
import com.google.gson.annotations.SerializedName

data class FieldDataEI(
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

fun FieldDataEM.toDomain() =
    FieldDataEI(
        aMaterno,
        aPaterno,
        area,
        cP,
        cURP,
        calle,
        ciudad,
        codigoEmpleado,
        colonia,
        estado,
        fechaNacimiento,
        fotografia,
        municipio,
        nombre,
        numInt,
        privilegios,
        rFC,
        sexo,
        statusLaboral,
        sueldoComisionista,
        sueldoOperativo,
        username
    )

fun EmployeeEntity.toDomain() =
    FieldDataEI(
        aMaterno,
        aPaterno,
        area,
        cP,
        cURP,
        calle,
        ciudad,
        codigoEmpleado,
        colonia,
        estado,
        fechaNacimiento,
        fotografia,
        municipio,
        nombre,
        numInt,
        privilegios,
        rFC,
        sexo,
        statusLaboral,
        sueldoComisionista,
        sueldoOperativo,
        username
    )