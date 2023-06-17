package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.ManifestRepository
import javax.inject.Inject

class GetManifestUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository,
    private val reloginUseCase: ReloginUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase
) {

    suspend operator fun invoke(date: String) {
        reloginUseCase
        val employee = loadEmployeeUseCase.invoke()
        val nameEmployee = "${employee.nombre} ${employee.aPaterno} ${employee.aMaterno}"
        val data: List<String> = listOf(date, nameEmployee, "3")
        val response = manifestRepository.getManifest(data)

        Log.d("Manifest", "$response")
    }
}