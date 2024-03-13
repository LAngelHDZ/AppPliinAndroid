package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.EmployeeRepository
import com.example.pliin.apppliin.domain.model.emproyeeitem.DataEI
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import javax.inject.Inject


class GetAllEmployeesUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val reloginUseCase: ReloginUseCase
) {
    suspend operator fun invoke(): List<DataEI?>? {
        reloginUseCase()
        val response = employeeRepository.getAllEmployeeApi()
        return response?.response?.data
    }
}