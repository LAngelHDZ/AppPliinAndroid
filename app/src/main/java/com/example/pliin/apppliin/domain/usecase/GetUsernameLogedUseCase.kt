package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.UsersRepository
import com.example.pliin.apppliin.domain.model.UserItem
import javax.inject.Inject

class GetUsernameLogedUseCase @Inject constructor( private val usersRepository: UsersRepository){

    suspend operator fun invoke(): UserItem {
        return   usersRepository.getAllUserDatabase()
    }
}