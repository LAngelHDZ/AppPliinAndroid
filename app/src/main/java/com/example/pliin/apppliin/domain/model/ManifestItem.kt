package com.example.pliin.apppliin.domain.model


import java.util.Date

data class ManifestItem(
    val manifestKey: String,
    val fkEmpleye: String,
    val company: String?,
    val createDate: Date?,
    val route: String?,
    val routeStatus: String?,
    val manifestStatus: String?,
    val totalPackages: Int?,
    val totalGuides: Int?
)
