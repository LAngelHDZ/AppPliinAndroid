package com.example.pliin.apppliin.domain.usecase.login

import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.repositories.LoginRepository
import javax.inject.Inject

class GetTokenLocalUseCase @Inject constructor(
    private val tokenDao: TokenDao,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Boolean {
        return loginRepository.getToken().equals("")
    }
}