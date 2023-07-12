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


        val nameEmployee = if(employee.area.equals("Operador Logistico")){
            "${employee.nombre} ${employee.aPaterno} ${employee.aMaterno}"
        }else{
            ""
        }

        val  limit = if(employee.area.equals("Operador Logistico")){
            "5"
        }else{
            "20"
        }
        val data: List<String> = listOf(date, nameEmployee, limit)
        val response = manifestRepository.getManifest(data)
        Log.d("Manifest", "$response")
        return response.response?.data
    }
}