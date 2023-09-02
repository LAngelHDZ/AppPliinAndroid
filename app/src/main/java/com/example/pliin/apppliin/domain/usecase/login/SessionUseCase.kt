package com.example.pliin.apppliin.domain.usecase.login

import com.example.pliin.apppliin.data.repositories.LoginRepository
import javax.inject.Inject

class SessionUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke():Boolean{
        return loginRepository.onSession()
    }
}