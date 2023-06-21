package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.ManifestRepository
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.FieldData
import javax.inject.Inject

class GetManifestUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository,
    private val reloginUseCase: ReloginUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase
) {

    suspend operator fun invoke(date: String): List<Data?>? {
        reloginUseCase
        val employee = loadEmployeeUseCase.invoke()
        val nameEmployee = "${employee.nombre} ${employee.aPaterno} ${employee.aMaterno}"
        val data: List<String> = listOf(date, nameEmployee, "3")
        val response = manifestRepository.getManifest(data)

        return response.response?.data
        Log.d("Manifest", "$response")
    }
}