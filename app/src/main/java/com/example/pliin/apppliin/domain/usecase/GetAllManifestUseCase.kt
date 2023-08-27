package com.example.pliin.apppliin.domain.usecase

import android.util.Log
import com.example.pliin.apppliin.data.repositories.ManifestRepository
import com.example.pliin.apppliin.domain.model.consecutivomanifestitem.Data
import javax.inject.Inject

class GetAllManifestUseCase @Inject constructor(
    private val manifestRepository: ManifestRepository,
    private val reloginUseCase: ReloginUseCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase
) {

    suspend operator fun invoke(date: String): List<Data?>? {
       val onSession= reloginUseCase.invoke()
        Log.i("sesion","$onSession")

        return  if (onSession){
            val employee = loadEmployeeUseCase.invoke()

            val nameEmployee = if(employee.area.equals("Operador Logistico")){
                "${employee.nombre} ${employee.aPaterno} ${employee.aMaterno}"
            }else{
                ""
            }

            val  statusPreM = if(employee.area.equals("Operador Logistico")){
                "APLICADO"
            }else{
                "NO APLICADO"
            }

            val  limit = if(employee.area.equals("Operador Logistico")){
                "5"
            }else{
                "50"
            }

            val data: List<String> = listOf(date, nameEmployee, limit,"",statusPreM)
            val response = manifestRepository.getManifest(data)
            Log.d("Manifest", "$response")
            response.response?.data
        }else{
            emptyList()
        }
    }
}