package com.example.pliin.apppliin.domain.usecase

import com.example.pliin.apppliin.data.repositories.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke():Boolean{
      return  if (loginRepository.tokenDBExists()){
          val response = loginRepository.dologout()
          (response.messagesLogout!![0]?.code.equals("0") and response.messagesLogout!![0]?.message.equals(
              "OK"
          )) || (response.messagesLogout!![0]?.code.equals("952"))
        }else{
          true
        }
    }
}