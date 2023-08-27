package com.example.pliin.apppliin.domain.usecase
import android.annotation.SuppressLint
import com.example.pliin.apppliin.data.database.dao.UserDao
import com.example.pliin.apppliin.data.repositories.LoginRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class ReloginUseCase @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val usersRepository: UsersRepository,
    private val loginRepository: LoginRepository
){
    suspend operator fun invoke(): Boolean{
        val userData = loginRepository.getSession()
        return loginUseCase.invoke(userData.username!!, userData.password!!)
    }
}