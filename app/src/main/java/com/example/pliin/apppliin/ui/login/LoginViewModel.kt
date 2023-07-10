package com.example.pliin.apppliin.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pliin.apppliin.domain.usecase.GetGuideUseCase
import com.example.pliin.apppliin.domain.usecase.GetUserUseCase
import com.example.pliin.apppliin.domain.usecase.LoginUseCase
import com.example.pliin.apppliin.generals.GeneralMethodsGuide
import com.example.pliin.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getGuideUseCase: GetGuideUseCase,
    private val generalMethodsGuide: GeneralMethodsGuide
) :
    ViewModel() {

    private val _isSesionDialog = MutableLiveData<Boolean>()
    var isSesionDialog: LiveData<Boolean> = _isSesionDialog

    private val _isLoginLoading = MutableLiveData<Boolean>()
    var isLoginLoading: LiveData<Boolean> = _isLoginLoading

    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _messageDialog = MutableLiveData<String>()
    val messageDialog: LiveData<String> = _messageDialog

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    fun onLoginChanged(user: String, password: String) {
        _user.value = user
        _password.value = password
        _isLoginEnable.value = enableLogin(user, password)
    }

    private fun enableLogin(user: String, password: String) =
        user.length > 4 && password.isNotEmpty()

    fun onCreate() {
        viewModelScope.launch {
            val result = getUserUseCase()
            if (!result.isNullOrEmpty()) {
                Log.i("System", "User recuperados")
                Log.i("System", result.toString())
            } else {
                Log.i("System", "No hay usuarios")
                Log.i("System", result.toString())
            }
        }
    }

    fun onSesionDialog() {
        _isSesionDialog.value = false
    }

    fun messageDialog(message: String) {
        _messageDialog.value = message
        _isLoginLoading.value = false
        _isSesionDialog.value = true
    }

    fun onLoginSelected(navigationController: NavHostController) {
        _isLoginLoading.value = true
        if (generalMethodsGuide.checkInternetConnection()) {
            viewModelScope.launch {
                delay(1000)
                val tokenEmpty = loginUseCase(user.value!!, password.value!!)
                if (tokenEmpty) {
                    _isLoginLoading.value = false
                    messageDialog("El usuario o la contraseña son incorrectos")
                    Log.i("System", "Invalid User")
                } else {
                    Log.i("System", "result OK")
                    _user.value = ""
                    _password.value = ""
                    _isLoginEnable.value = false
                    navigationController.navigate(AppScreen.AppMainScreen.route)
                    _isLoginLoading.value = false
                }
            }
        } else {
            messageDialog("Error de comunicación, favor de verificar la conexión a internet")
        }
    }
}