package com.example.pliin.apppliin.domain.usecase.user

import com.example.pliin.apppliin.data.repositories.LoginRepository
import com.example.pliin.apppliin.data.repositories.UsersRepository
import javax.inject.Inject

class DeleteUserUSeCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke() {
        loginRepository.clearToken()
        usersRepository.clearUser()
    }
}