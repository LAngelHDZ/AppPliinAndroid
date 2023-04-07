package com.example.pliin.apppliin.domain

import com.example.pliin.apppliin.data.UsersRepository
import com.example.pliin.apppliin.data.database.entities.toDatabase
import com.example.pliin.apppliin.data.model.UserModel
import com.example.pliin.apppliin.domain.model.UserItem
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UsersRepository) {

  suspend operator fun invoke():List<UserItem>?{
      /*
       val users = repository.getAllUser()
       return users
*/
        val users = repository.getAllUserFromApi()

       if (users.isNotEmpty()){
          repository.clearUser()
            repository.insertUsers(users.map { it.toDatabase() })
        }else{

        }
     // return repository.getAllUserFromApi()
return users

    }
}