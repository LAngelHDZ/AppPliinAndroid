package com.example.pliin.apppliin.domain.usecase


import android.util.Log
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.data.repositories.EmployeeRepository
import com.example.pliin.apppliin.data.repositories.LoginRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val Loginrepository: LoginRepository,
    private val usersRepository: UsersRepository,
    private val employeeRepository: EmployeeRepository,
    private val generalMethodsGuide: GeneralMethodsGuide
) {
    suspend operator fun invoke(
        user: String,
        password: String
    ): Boolean {
        // usersRepository.clearUser()
        val userexiste =
            usersRepository.getLoginUserDatabaseB(generalMethodsGuide.toLowerLetter(user), password)
        var userValidate: String = ""
        var employeeActive: String = ""
        val access: Boolean = if (userexiste) {
//            val token: String = Loginrepository.getTokenDB()?.token.toString()
            Loginrepository.CreateSession(user, password, "")
            true
        }else{
            val sessionAPi = Loginrepository.dologin(user, password)
            val sessionOk = if (!sessionAPi.token.isNullOrEmpty()){
                Loginrepository.CreateSession(user, password, sessionAPi.token)
                Loginrepository.clearToken()
                Loginrepository.insertToken(sessionAPi.toDatabase())
                Log.i("GetTokenAPI", sessionAPi.token.toString())
                Log.i("GetTokenDB", Loginrepository.getTokenDB()?.token.toString())
                val dataUser = usersRepository.SetUserItem(user, password)
                usersRepository.insertUsers(dataUser.map { it.toDatabase() })
                val existeEmployee = employeeRepository.queryDataEmployee(generalMethodsGuide.toLowerLetter(user))
                if (!existeEmployee) {
                    val data = employeeRepository.getEmployeeApi(user)
                    employeeRepository.saveDataEmployee(data)
                }
                Log.i("access ok", "true")
                true
            } else {
                Log.i("acces denegate", "false")
                false
            }
            sessionOk
        }
        return access
    }
}