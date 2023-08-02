package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.LoginRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class CloseSessionUseCase @Inject constructor(
    private val loginRepository: LoginRepository
    ) {
    suspend operator fun invoke(){
        loginRepository.closeSession()
        loginRepository.clearToken()
    }
}