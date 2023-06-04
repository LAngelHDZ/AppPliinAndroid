package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.EmployeeRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import javax.inject.Inject

class LoadEmployeeUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(): FieldDataEI {
        val user = usersRepository.getAllUserDatabase()[0].user
        return employeeRepository.getEmployeeDB(user!!)
    }
}