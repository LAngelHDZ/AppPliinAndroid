package com.example.pliin.apppliin.domain.usecase


import android.util.Log
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.data.repositories.EmployeeRepository
import com.example.pliin.apppliin.data.repositories.LoginRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val Loginrepository: LoginRepository,
    private val usersRepository: UsersRepository,
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(
        user: String,
        password: String
    ): Boolean {
        usersRepository.clearUser()
        val userexiste = usersRepository.getLoginUserDatabaseB(user, password)
        var userValidate: String = ""
        var employeeActive: String = ""

        if (!userexiste) {
            val dataUser = usersRepository.SetUserItem(user, password)
            usersRepository.insertUsers(dataUser.map { it.toDatabase() })
        }

        val token = Loginrepository.dologin(user, password)
        if (!token.token.isNullOrEmpty()) {
            Loginrepository.clearToken()
            Loginrepository.insertToken(token.toDatabase())
            Log.i("GetTokenAPI", token.token.toString())
            Log.i("GetTokenDB", Loginrepository.getTokenDB().token.toString())
            userValidate = Loginrepository.getTokenDB().token!!
            val existeEmployee = employeeRepository.queryDataEmployee(user)
            if (!existeEmployee) {
                val data = employeeRepository.getEmployeeApi(user)
                employeeRepository.saveDataEmployee(data)
            }
            employeeActive = employeeRepository.getEmployeeDB(user).statusLaboral.toString()
        }
        return token.token.isNullOrEmpty()
    }
}