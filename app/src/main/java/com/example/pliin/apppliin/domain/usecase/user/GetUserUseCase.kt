package com.example.pliin.apppliin.domain.usecase.user

import com.example.pliin.apppliin.data.repositories.UsersRepository
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.domain.model.UserItem
import com.example.pliin.apppliin.domain.model.toDomain
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(): String {
        /*
         val users = repository.getAllUser()
         return users
  *//*
        val users = repository.getAllUserFromApi()

       if (users.isNotEmpty()){
          repository.clearUser()
            repository.insertUsers(users.map { it.toDatabase() })
        }else{

        }
     // return repository.getAllUserFromApi()*/
        return "users"

    }
}