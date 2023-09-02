package com.example.pliin.apppliin.ui.appmain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.login.CloseSessionUseCase
import com.example.pliin.apppliin.domain.usecase.user.DeleteUserUSeCase
import com.example.pliin.apppliin.domain.usecase.user.LoadEmployeeUseCase
import com.example.pliin.apppliin.domain.usecase.login.LogoutUseCase
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainAppViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUSeCase: DeleteUserUSeCase,
    private val loadEmployeeUseCase: LoadEmployeeUseCase,
    private val closeSessionUseCase: CloseSessionUseCase
) : ViewModel() {

    private val _isLogout = MutableLiveData<Boolean>()
    var isLogout: LiveData<Boolean> = _isLogout
    private val _isLoged = MutableLiveData<Boolean>()
    var isLoged: LiveData<Boolean> = _isLoged

    private val _nameEmployye = MutableLiveData<String>()
    var nameEmployee: LiveData<String> = _nameEmployye

    private val _areaEmployye = MutableLiveData<String>()
    var areaEmployee: LiveData<String> = _areaEmployye

    fun logut(navigationController: NavHostController) {
        _isLoged.value = false
        _isLogout.value = true
        viewModelScope.launch {
            delay(1500)
            if (logoutUseCase.invoke()) {
                closeSessionUseCase.invoke()
                navigate(navigationController)

            }
        }
    }

    fun gsaveDataEmployee(name:String,area:String) {
            _nameEmployye.value = name
            _areaEmployye.value = area
            _isLoged.value=false
    }

    private fun navigate(navController: NavHostController) {
        val previousScreenName = navController.previousBackStackEntry?.destination?.route
        if (previousScreenName.equals("LoginScreen")) {

            navController.popBackStack()
            _isLogout.value = false
        } else {
            navController.navigate(AppScreen.LoginScreen.route)
            _isLogout.value = false
        }
    }
}