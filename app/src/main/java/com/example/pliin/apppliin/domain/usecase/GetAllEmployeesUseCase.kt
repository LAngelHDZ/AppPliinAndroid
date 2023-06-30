package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.EmployeeRepository
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import javax.inject.Inject


class GetAllEmployeesUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) {

    /* suspend operator fun invoke():List<FieldDataEI>{


     }*/
}