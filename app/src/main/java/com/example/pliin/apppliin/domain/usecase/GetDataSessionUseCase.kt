package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.LoginRepository
import javax.inject.Inject

class GetDataSessionUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() = loginRepository.getSession()
}