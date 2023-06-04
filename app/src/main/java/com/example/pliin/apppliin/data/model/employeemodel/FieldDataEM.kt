package com.example.pliin.apppliin.data.model.employeemodel


import com.google.gson.annotations.SerializedName

data class FieldDataEM(
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