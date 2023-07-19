package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.EmployeeRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import com.example.pliin.apppliin.domain.model.emproyeeitem.FieldDataEI
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import javax.inject.Inject

class LoadEmployeeUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val usersRepository: UsersRepository,
    private val generalMethodsGuide: GeneralMethodsGuide
) {

    suspend operator fun invoke(): FieldDataEI {
        val user = usersRepository.getAllUserDatabase()
        return employeeRepository.getEmployeeDB(generalMethodsGuide.toLowerLetter(user.user!!))
    }
}