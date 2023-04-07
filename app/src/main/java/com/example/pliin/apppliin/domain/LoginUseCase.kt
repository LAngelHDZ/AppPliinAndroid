package com.example.pliin.apppliin.domain

import android.util.Log
import com.example.pliin.apppliin.data.LoginRepository
import com.example.pliin.apppliin.data.UsersRepository
import com.example.pliin.apppliin.data.model.providers.UserProvider
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val Loginrepository: LoginRepository, private val userProvider: UserProvider, private val usersRepository: UsersRepository) {

   suspend  operator fun invoke(
        user: String,
        password: String
    ):Boolean {
        /*var result:Boolean = false
      val userLogin = usersRepository.getLoginUserDatabase(user =
      user, password = password)*/
      val userLoginCount = usersRepository.getLoginUserDatabaseB(user =
      user, password = password)
       // Log.i("User Database", userLogin.toString())
       Log.i("User DatabaseCount", userLoginCount.toString())

      /* userLogin.forEach{
           if ( it.user.equals(user)  && it.password.equals(password)){
                result = true
            }
        }
        return userLoginCount*/
       return usersRepository.getLoginUserDatabaseB(user =
       user, password = password)
    }
}