package com.example.pliin.apppliin.ui.mainloading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.data.network.dto.UserDTO
import com.example.pliin.apppliin.data.repositories.UsersRepository
import com.example.pliin.apppliin.domain.usecase.GetTokenLocalUseCase
import com.example.pliin.apppliin.domain.usecase.LoginUseCase
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MLViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val getTokenLocalUseCase: GetTokenLocalUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    fun noToken(navigationController: NavHostController) {

        viewModelScope.launch {
            if (!getTokenLocalUseCase.invoke()) {
                doLogin(navigationController)
            } else {
                navigationController.popBackStack()
                navigationController.navigate(AppScreen.LoginScreen.route)
            }
        }
    }

    fun doLogin(navigationController: NavHostController) {
        viewModelScope.launch {
            val user = usersRepository.getAllUserDatabase()
            loginUseCase(user[0].user!!, user[0].password!!)
            navigationController.popBackStack()
            navigationController.navigate(AppScreen.AppMainScreen.route)

        }


    }
}